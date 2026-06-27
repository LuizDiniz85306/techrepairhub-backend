package com.luiz.techrepairhub.service;

import com.luiz.techrepairhub.dto.ProdutoAtualizarDTO;
import com.luiz.techrepairhub.dto.ProdutoCadastroDTO;
import com.luiz.techrepairhub.entity.Categoria;
import com.luiz.techrepairhub.entity.EstadoConservacao;
import com.luiz.techrepairhub.entity.Produto;
import com.luiz.techrepairhub.repository.CategoriaRepository;
import com.luiz.techrepairhub.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProdutoService(
            ProdutoRepository produtoRepository,
            CategoriaRepository categoriaRepository
    ) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public Produto cadastrar(ProdutoCadastroDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com id: " + dto.getCategoriaId()));

        if (!categoria.getAtivo()) {
            throw new RuntimeException("Não é possível cadastrar produto em categoria inativa.");
        }

        Produto produto = new Produto(
                dto.getNome(),
                dto.getDescricao(),
                dto.getPreco(),
                dto.getEstadoConservacao(),
                dto.getGarantiaMeses(),
                categoria
        );

        return produtoRepository.save(produto);
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public List<Produto> listarAtivos() {
        return produtoRepository.findByAtivoTrue();
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com id: " + id));
    }

    public List<Produto> buscarPorNome(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Produto> buscarPorCategoria(Long categoriaId) {
        return produtoRepository.findByCategoriaId(categoriaId);
    }

    public List<Produto> buscarAtivosPorCategoria(Long categoriaId) {
        return produtoRepository.findByCategoriaIdAndAtivoTrue(categoriaId);
    }

    public List<Produto> buscarPorEstadoConservacao(EstadoConservacao estadoConservacao) {
        return produtoRepository.findByEstadoConservacao(estadoConservacao);
    }

    public List<Produto> buscarPorFaixaPreco(BigDecimal precoMinimo, BigDecimal precoMaximo) {
        return produtoRepository.findByPrecoBetween(precoMinimo, precoMaximo);
    }

    public Produto atualizar(Long id, ProdutoAtualizarDTO dto) {
        Produto produto = buscarPorId(id);

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com id: " + dto.getCategoriaId()));

        if (!categoria.getAtivo()) {
            throw new RuntimeException("Não é possível vincular produto a categoria inativa.");
        }

        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setEstadoConservacao(dto.getEstadoConservacao());
        produto.setGarantiaMeses(dto.getGarantiaMeses());
        produto.setCategoria(categoria);

        return produtoRepository.save(produto);
    }

    public Produto inativar(Long id) {
        Produto produto = buscarPorId(id);
        produto.setAtivo(false);
        return produtoRepository.save(produto);
    }

    public Produto reativar(Long id) {
        Produto produto = buscarPorId(id);
        produto.setAtivo(true);
        return produtoRepository.save(produto);
    }
}