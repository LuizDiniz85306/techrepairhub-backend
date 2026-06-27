package com.luiz.techrepairhub.service;

import com.luiz.techrepairhub.dto.AdicionarItemCarrinhoDTO;
import com.luiz.techrepairhub.dto.AtualizarItemCarrinhoDTO;
import com.luiz.techrepairhub.dto.CarrinhoResumoDTO;
import com.luiz.techrepairhub.dto.ItemCarrinhoResumoDTO;
import com.luiz.techrepairhub.entity.Carrinho;
import com.luiz.techrepairhub.entity.Cliente;
import com.luiz.techrepairhub.entity.ItemCarrinho;
import com.luiz.techrepairhub.entity.Produto;
import com.luiz.techrepairhub.repository.CarrinhoRepository;
import com.luiz.techrepairhub.repository.ClienteRepository;
import com.luiz.techrepairhub.repository.ItemCarrinhoRepository;
import com.luiz.techrepairhub.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarrinhoService {

    private final CarrinhoRepository carrinhoRepository;
    private final ItemCarrinhoRepository itemCarrinhoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public CarrinhoService(
            CarrinhoRepository carrinhoRepository,
            ItemCarrinhoRepository itemCarrinhoRepository,
            ClienteRepository clienteRepository,
            ProdutoRepository produtoRepository
    ) {
        this.carrinhoRepository = carrinhoRepository;
        this.itemCarrinhoRepository = itemCarrinhoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    public Carrinho criarCarrinho(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com id: " + clienteId));

        if (!cliente.getAtivo()) {
            throw new RuntimeException("Não é possível criar carrinho para cliente inativo.");
        }

        if (carrinhoRepository.existsByClienteId(clienteId)) {
            throw new RuntimeException("Este cliente já possui carrinho.");
        }

        Carrinho carrinho = new Carrinho(cliente);

        return carrinhoRepository.save(carrinho);
    }

    public Carrinho buscarOuCriarCarrinho(Long clienteId) {
        return carrinhoRepository.findByClienteId(clienteId)
                .orElseGet(() -> criarCarrinho(clienteId));
    }

    public Carrinho buscarCarrinhoPorCliente(Long clienteId) {
        return carrinhoRepository.findByClienteId(clienteId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado para o cliente id: " + clienteId));
    }

    @Transactional
    public CarrinhoResumoDTO adicionarItem(Long clienteId, AdicionarItemCarrinhoDTO dto) {
        Carrinho carrinho = buscarOuCriarCarrinho(clienteId);

        Produto produto = produtoRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com id: " + dto.getProdutoId()));

        if (!produto.getAtivo()) {
            throw new RuntimeException("Não é possível adicionar produto inativo ao carrinho.");
        }

        itemCarrinhoRepository.findByCarrinhoIdAndProdutoId(carrinho.getId(), produto.getId())
                .ifPresentOrElse(
                        itemExistente -> {
                            itemExistente.setQuantidade(itemExistente.getQuantidade() + dto.getQuantidade());
                            itemCarrinhoRepository.save(itemExistente);
                        },
                        () -> {
                            ItemCarrinho novoItem = new ItemCarrinho(carrinho, produto, dto.getQuantidade());
                            itemCarrinhoRepository.save(novoItem);
                        }
                );

        carrinho.atualizarData();
        carrinhoRepository.save(carrinho);

        return montarResumo(carrinho.getId());
    }

    @Transactional
    public CarrinhoResumoDTO atualizarQuantidadeItem(Long itemId, AtualizarItemCarrinhoDTO dto) {
        ItemCarrinho item = itemCarrinhoRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item do carrinho não encontrado com id: " + itemId));

        item.setQuantidade(dto.getQuantidade());
        itemCarrinhoRepository.save(item);

        Carrinho carrinho = item.getCarrinho();
        carrinho.atualizarData();
        carrinhoRepository.save(carrinho);

        return montarResumo(carrinho.getId());
    }

    @Transactional
    public CarrinhoResumoDTO removerItem(Long itemId) {
        ItemCarrinho item = itemCarrinhoRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item do carrinho não encontrado com id: " + itemId));

        Long carrinhoId = item.getCarrinho().getId();

        itemCarrinhoRepository.delete(item);

        Carrinho carrinho = carrinhoRepository.findById(carrinhoId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado com id: " + carrinhoId));

        carrinho.atualizarData();
        carrinhoRepository.save(carrinho);

        return montarResumo(carrinhoId);
    }

    @Transactional
    public CarrinhoResumoDTO limparCarrinho(Long clienteId) {
        Carrinho carrinho = buscarCarrinhoPorCliente(clienteId);

        itemCarrinhoRepository.deleteByCarrinhoId(carrinho.getId());

        carrinho.atualizarData();
        carrinhoRepository.save(carrinho);

        return montarResumo(carrinho.getId());
    }

    public CarrinhoResumoDTO resumoPorCliente(Long clienteId) {
        Carrinho carrinho = buscarOuCriarCarrinho(clienteId);
        return montarResumo(carrinho.getId());
    }

    public CarrinhoResumoDTO montarResumo(Long carrinhoId) {
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado com id: " + carrinhoId));

        List<ItemCarrinho> itens = itemCarrinhoRepository.findByCarrinhoId(carrinho.getId());

        List<ItemCarrinhoResumoDTO> itensResumo = itens.stream()
                .map(item -> new ItemCarrinhoResumoDTO(
                        item.getId(),
                        item.getProduto().getId(),
                        item.getProduto().getNome(),
                        item.getQuantidade(),
                        item.getPrecoUnitario(),
                        item.calcularSubtotal()
                ))
                .toList();

        return new CarrinhoResumoDTO(
                carrinho.getId(),
                carrinho.getCliente().getId(),
                carrinho.getCliente().getUsuario().getNome(),
                itensResumo,
                itensResumo.stream()
                        .map(ItemCarrinhoResumoDTO::getSubtotal)
                        .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add)
        );
    }
}
