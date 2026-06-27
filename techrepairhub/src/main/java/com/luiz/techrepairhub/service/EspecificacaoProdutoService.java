package com.luiz.techrepairhub.service;

import com.luiz.techrepairhub.dto.EspecificacaoProdutoAtualizarDTO;
import com.luiz.techrepairhub.dto.EspecificacaoProdutoCadastroDTO;
import com.luiz.techrepairhub.entity.EspecificacaoProduto;
import com.luiz.techrepairhub.entity.Produto;
import com.luiz.techrepairhub.repository.EspecificacaoProdutoRepository;
import com.luiz.techrepairhub.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspecificacaoProdutoService {

    private final EspecificacaoProdutoRepository especificacaoProdutoRepository;
    private final ProdutoRepository produtoRepository;

    public EspecificacaoProdutoService(
            EspecificacaoProdutoRepository especificacaoProdutoRepository,
            ProdutoRepository produtoRepository
    ) {
        this.especificacaoProdutoRepository = especificacaoProdutoRepository;
        this.produtoRepository = produtoRepository;
    }

    public EspecificacaoProduto cadastrar(EspecificacaoProdutoCadastroDTO dto) {
        Produto produto = produtoRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com id: " + dto.getProdutoId()));

        if (!produto.getAtivo()) {
            throw new RuntimeException("Não é possível adicionar especificação a um produto inativo.");
        }

        if (especificacaoProdutoRepository.existsByProdutoIdAndNomeIgnoreCase(produto.getId(), dto.getNome())) {
            throw new RuntimeException("Este produto já possui uma especificação com este nome.");
        }

        EspecificacaoProduto especificacao = new EspecificacaoProduto(
                dto.getNome(),
                dto.getValor(),
                produto
        );

        return especificacaoProdutoRepository.save(especificacao);
    }

    public List<EspecificacaoProduto> listarTodas() {
        return especificacaoProdutoRepository.findAll();
    }

    public List<EspecificacaoProduto> listarPorProduto(Long produtoId) {
        return especificacaoProdutoRepository.findByProdutoId(produtoId);
    }

    public List<EspecificacaoProduto> listarAtivasPorProduto(Long produtoId) {
        return especificacaoProdutoRepository.findByProdutoIdAndAtivoTrue(produtoId);
    }

    public EspecificacaoProduto buscarPorId(Long id) {
        return especificacaoProdutoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especificação não encontrada com id: " + id));
    }

    public List<EspecificacaoProduto> buscarPorNome(String nome) {
        return especificacaoProdutoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<EspecificacaoProduto> buscarPorProdutoENome(Long produtoId, String nome) {
        return especificacaoProdutoRepository.findByProdutoIdAndNomeContainingIgnoreCase(produtoId, nome);
    }

    public EspecificacaoProduto atualizar(Long id, EspecificacaoProdutoAtualizarDTO dto) {
        EspecificacaoProduto especificacao = buscarPorId(id);

        especificacaoProdutoRepository.findByProdutoIdAndNomeIgnoreCase(
                especificacao.getProduto().getId(),
                dto.getNome()
        ).ifPresent(especificacaoExistente -> {
            if (!especificacaoExistente.getId().equals(id)) {
                throw new RuntimeException("Este produto já possui outra especificação com este nome.");
            }
        });

        especificacao.setNome(dto.getNome());
        especificacao.setValor(dto.getValor());

        return especificacaoProdutoRepository.save(especificacao);
    }

    public EspecificacaoProduto inativar(Long id) {
        EspecificacaoProduto especificacao = buscarPorId(id);
        especificacao.setAtivo(false);
        return especificacaoProdutoRepository.save(especificacao);
    }

    public EspecificacaoProduto reativar(Long id) {
        EspecificacaoProduto especificacao = buscarPorId(id);
        especificacao.setAtivo(true);
        return especificacaoProdutoRepository.save(especificacao);
    }
}