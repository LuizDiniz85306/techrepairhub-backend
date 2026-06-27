package com.luiz.techrepairhub.service;

import com.luiz.techrepairhub.dto.EstoqueAtualizarDTO;
import com.luiz.techrepairhub.dto.EstoqueCadastroDTO;
import com.luiz.techrepairhub.entity.Estoque;
import com.luiz.techrepairhub.entity.Produto;
import com.luiz.techrepairhub.repository.EstoqueRepository;
import com.luiz.techrepairhub.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;
    private final ProdutoRepository produtoRepository;

    public EstoqueService(
            EstoqueRepository estoqueRepository,
            ProdutoRepository produtoRepository
    ) {
        this.estoqueRepository = estoqueRepository;
        this.produtoRepository = produtoRepository;
    }

    public Estoque cadastrar(EstoqueCadastroDTO dto) {
        Produto produto = produtoRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com id: " + dto.getProdutoId()));

        if (!produto.getAtivo()) {
            throw new RuntimeException("Não é possível criar estoque para produto inativo.");
        }

        if (estoqueRepository.existsByProdutoId(produto.getId())) {
            throw new RuntimeException("Este produto já possui estoque cadastrado.");
        }

        Estoque estoque = new Estoque(
                produto,
                dto.getQuantidadeAtual(),
                dto.getEstoqueMinimo()
        );

        return estoqueRepository.save(estoque);
    }

    public List<Estoque> listarTodos() {
        return estoqueRepository.findAll();
    }

    public List<Estoque> listarAtivos() {
        return estoqueRepository.findByAtivoTrue();
    }

    public Estoque buscarPorId(Long id) {
        return estoqueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado com id: " + id));
    }

    public Estoque buscarPorProdutoId(Long produtoId) {
        return estoqueRepository.findByProdutoId(produtoId)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado para o produto id: " + produtoId));
    }

    public Estoque atualizar(Long id, EstoqueAtualizarDTO dto) {
        Estoque estoque = buscarPorId(id);

        estoque.setQuantidadeAtual(dto.getQuantidadeAtual());
        estoque.setEstoqueMinimo(dto.getEstoqueMinimo());

        return estoqueRepository.save(estoque);
    }

    public Estoque atualizarPorProduto(Long produtoId, EstoqueAtualizarDTO dto) {
        Estoque estoque = buscarPorProdutoId(produtoId);

        estoque.setQuantidadeAtual(dto.getQuantidadeAtual());
        estoque.setEstoqueMinimo(dto.getEstoqueMinimo());

        return estoqueRepository.save(estoque);
    }

    public List<Estoque> listarComEstoqueDisponivel() {
        return estoqueRepository.findByQuantidadeAtualGreaterThan(0);
    }

    public List<Estoque> listarSemEstoque() {
        return estoqueRepository.findByQuantidadeAtualLessThanEqual(0);
    }

    public List<Estoque> listarAbaixoDoMinimo() {
        return estoqueRepository.findAll()
                .stream()
                .filter(Estoque::estaAbaixoDoMinimo)
                .toList();
    }

    public Estoque inativar(Long id) {
        Estoque estoque = buscarPorId(id);
        estoque.setAtivo(false);
        return estoqueRepository.save(estoque);
    }

    public Estoque reativar(Long id) {
        Estoque estoque = buscarPorId(id);
        estoque.setAtivo(true);
        return estoqueRepository.save(estoque);
    }
}