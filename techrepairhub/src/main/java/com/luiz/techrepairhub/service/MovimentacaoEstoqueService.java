package com.luiz.techrepairhub.service;

import com.luiz.techrepairhub.dto.MovimentacaoEstoqueCadastroDTO;
import com.luiz.techrepairhub.entity.Estoque;
import com.luiz.techrepairhub.entity.MovimentacaoEstoque;
import com.luiz.techrepairhub.entity.TipoMovimentacaoEstoque;
import com.luiz.techrepairhub.repository.EstoqueRepository;
import com.luiz.techrepairhub.repository.MovimentacaoEstoqueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovimentacaoEstoqueService {

    private final MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;
    private final EstoqueRepository estoqueRepository;

    public MovimentacaoEstoqueService(
            MovimentacaoEstoqueRepository movimentacaoEstoqueRepository,
            EstoqueRepository estoqueRepository
    ) {
        this.movimentacaoEstoqueRepository = movimentacaoEstoqueRepository;
        this.estoqueRepository = estoqueRepository;
    }

    @Transactional
    public MovimentacaoEstoque registrar(MovimentacaoEstoqueCadastroDTO dto) {
        Estoque estoque = estoqueRepository.findByProdutoId(dto.getProdutoId())
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado para o produto id: " + dto.getProdutoId()));

        if (!estoque.getAtivo()) {
            throw new RuntimeException("Não é possível movimentar um estoque inativo.");
        }

        aplicarMovimentacaoNoEstoque(estoque, dto);

        estoqueRepository.save(estoque);

        MovimentacaoEstoque movimentacao = new MovimentacaoEstoque(
                estoque,
                dto.getTipo(),
                dto.getQuantidade(),
                dto.getObservacao()
        );

        return movimentacaoEstoqueRepository.save(movimentacao);
    }

    private void aplicarMovimentacaoNoEstoque(Estoque estoque, MovimentacaoEstoqueCadastroDTO dto) {
        Integer quantidadeAtual = estoque.getQuantidadeAtual();
        Integer quantidadeMovimentada = dto.getQuantidade();

        if (dto.getTipo() == TipoMovimentacaoEstoque.ENTRADA) {
            estoque.setQuantidadeAtual(quantidadeAtual + quantidadeMovimentada);
            return;
        }

        if (dto.getTipo() == TipoMovimentacaoEstoque.SAIDA) {
            if (quantidadeAtual < quantidadeMovimentada) {
                throw new RuntimeException("Estoque insuficiente para realizar a saída.");
            }

            estoque.setQuantidadeAtual(quantidadeAtual - quantidadeMovimentada);
            return;
        }

        if (dto.getTipo() == TipoMovimentacaoEstoque.AJUSTE) {
            estoque.setQuantidadeAtual(quantidadeMovimentada);
        }
    }

    public List<MovimentacaoEstoque> listarTodas() {
        return movimentacaoEstoqueRepository.findAll();
    }

    public MovimentacaoEstoque buscarPorId(Long id) {
        return movimentacaoEstoqueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimentação não encontrada com id: " + id));
    }

    public List<MovimentacaoEstoque> listarPorEstoque(Long estoqueId) {
        return movimentacaoEstoqueRepository.findByEstoqueId(estoqueId);
    }

    public List<MovimentacaoEstoque> listarPorProduto(Long produtoId) {
        return movimentacaoEstoqueRepository.findByEstoqueProdutoId(produtoId);
    }

    public List<MovimentacaoEstoque> listarPorTipo(TipoMovimentacaoEstoque tipo) {
        return movimentacaoEstoqueRepository.findByTipo(tipo);
    }
}