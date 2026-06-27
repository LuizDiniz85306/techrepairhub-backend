package com.luiz.techrepairhub.service;

import com.luiz.techrepairhub.dto.ItemPedidoResumoDTO;
import com.luiz.techrepairhub.dto.MovimentacaoEstoqueCadastroDTO;
import com.luiz.techrepairhub.dto.PedidoResumoDTO;
import com.luiz.techrepairhub.entity.*;
import com.luiz.techrepairhub.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final CarrinhoRepository carrinhoRepository;
    private final ItemCarrinhoRepository itemCarrinhoRepository;
    private final EstoqueRepository estoqueRepository;
    private final MovimentacaoEstoqueService movimentacaoEstoqueService;
    private final GarantiaService garantiaService;

    public PedidoService(
        PedidoRepository pedidoRepository,
        ItemPedidoRepository itemPedidoRepository,
        CarrinhoRepository carrinhoRepository,
        ItemCarrinhoRepository itemCarrinhoRepository,
        EstoqueRepository estoqueRepository,
        MovimentacaoEstoqueService movimentacaoEstoqueService,
        GarantiaService garantiaService
) {
    this.pedidoRepository = pedidoRepository;
    this.itemPedidoRepository = itemPedidoRepository;
    this.carrinhoRepository = carrinhoRepository;
    this.itemCarrinhoRepository = itemCarrinhoRepository;
    this.estoqueRepository = estoqueRepository;
    this.movimentacaoEstoqueService = movimentacaoEstoqueService;
    this.garantiaService = garantiaService;
}

    @Transactional
    public PedidoResumoDTO finalizarPedido(Long clienteId) {
        Carrinho carrinho = carrinhoRepository.findByClienteId(clienteId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado para o cliente id: " + clienteId));

        List<ItemCarrinho> itensCarrinho = itemCarrinhoRepository.findByCarrinhoId(carrinho.getId());

        if (itensCarrinho.isEmpty()) {
            throw new RuntimeException("Não é possível finalizar pedido com carrinho vazio.");
        }

        validarEstoqueDosItens(itensCarrinho);

        Pedido pedido = new Pedido(carrinho.getCliente());
        pedido.setStatus(StatusPedido.CONFIRMADO);

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        BigDecimal valorTotal = BigDecimal.ZERO;

        for (ItemCarrinho itemCarrinho : itensCarrinho) {
            ItemPedido itemPedido = new ItemPedido(
                    pedidoSalvo,
                    itemCarrinho.getProduto(),
                    itemCarrinho.getQuantidade(),
                    itemCarrinho.getPrecoUnitario()
            );
        
            ItemPedido itemPedidoSalvo = itemPedidoRepository.save(itemPedido);
        
            garantiaService.gerarGarantia(pedidoSalvo, itemPedidoSalvo);
        
            valorTotal = valorTotal.add(itemPedidoSalvo.getSubtotal());
        
            baixarEstoque(itemCarrinho);
        }

        pedidoSalvo.setValorTotal(valorTotal);
        pedidoRepository.save(pedidoSalvo);

        itemCarrinhoRepository.deleteByCarrinhoId(carrinho.getId());

        return montarResumo(pedidoSalvo.getId());
    }

    private void validarEstoqueDosItens(List<ItemCarrinho> itensCarrinho) {
        for (ItemCarrinho item : itensCarrinho) {
            Estoque estoque = estoqueRepository.findByProdutoId(item.getProduto().getId())
                    .orElseThrow(() -> new RuntimeException(
                            "Estoque não encontrado para o produto: " + item.getProduto().getNome()
                    ));

            if (!estoque.getAtivo()) {
                throw new RuntimeException("Estoque inativo para o produto: " + item.getProduto().getNome());
            }

            if (estoque.getQuantidadeAtual() < item.getQuantidade()) {
                throw new RuntimeException(
                        "Estoque insuficiente para o produto: " + item.getProduto().getNome()
                );
            }
        }
    }

    private void baixarEstoque(ItemCarrinho itemCarrinho) {
        MovimentacaoEstoqueCadastroDTO dto = new MovimentacaoEstoqueCadastroDTO();
        dto.setProdutoId(itemCarrinho.getProduto().getId());
        dto.setTipo(TipoMovimentacaoEstoque.SAIDA);
        dto.setQuantidade(itemCarrinho.getQuantidade());
        dto.setObservacao("Saída automática gerada pelo pedido");

        movimentacaoEstoqueService.registrar(dto);
    }

    public List<PedidoResumoDTO> listarTodos() {
        return pedidoRepository.findAll()
                .stream()
                .map(pedido -> montarResumo(pedido.getId()))
                .toList();
    }

    public List<PedidoResumoDTO> listarPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId)
                .stream()
                .map(pedido -> montarResumo(pedido.getId()))
                .toList();
    }

    public List<PedidoResumoDTO> listarPorStatus(StatusPedido status) {
        return pedidoRepository.findByStatus(status)
                .stream()
                .map(pedido -> montarResumo(pedido.getId()))
                .toList();
    }

    public PedidoResumoDTO buscarPorId(Long pedidoId) {
        return montarResumo(pedidoId);
    }

    @Transactional
    public PedidoResumoDTO cancelar(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com id: " + pedidoId));

        if (pedido.getStatus() == StatusPedido.CANCELADO) {
            throw new RuntimeException("Pedido já está cancelado.");
        }

        if (pedido.getStatus() == StatusPedido.ENTREGUE) {
            throw new RuntimeException("Não é possível cancelar pedido já entregue.");
        }

        pedido.setStatus(StatusPedido.CANCELADO);
        pedidoRepository.save(pedido);

        return montarResumo(pedido.getId());
    }

    @Transactional
    public PedidoResumoDTO marcarComoEntregue(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com id: " + pedidoId));

        if (pedido.getStatus() == StatusPedido.CANCELADO) {
            throw new RuntimeException("Não é possível entregar pedido cancelado.");
        }

        pedido.setStatus(StatusPedido.ENTREGUE);
        pedidoRepository.save(pedido);

        return montarResumo(pedido.getId());
    }

    private PedidoResumoDTO montarResumo(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com id: " + pedidoId));

        List<ItemPedido> itens = itemPedidoRepository.findByPedidoId(pedido.getId());

        List<ItemPedidoResumoDTO> itensResumo = itens.stream()
                .map(item -> new ItemPedidoResumoDTO(
                        item.getId(),
                        item.getProduto().getId(),
                        item.getProduto().getNome(),
                        item.getQuantidade(),
                        item.getPrecoUnitario(),
                        item.getSubtotal()
                ))
                .toList();

        return new PedidoResumoDTO(
                pedido.getId(),
                pedido.getCliente().getId(),
                pedido.getCliente().getUsuario().getNome(),
                pedido.getStatus(),
                pedido.getValorTotal(),
                pedido.getDataPedido(),
                itensResumo
        );
    }
}