package com.luiz.techrepairhub.controller;

import com.luiz.techrepairhub.dto.ProdutoAtualizarDTO;
import com.luiz.techrepairhub.dto.ProdutoCadastroDTO;
import com.luiz.techrepairhub.entity.EstadoConservacao;
import com.luiz.techrepairhub.entity.Produto;
import com.luiz.techrepairhub.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public Produto cadastrar(@RequestBody @Valid ProdutoCadastroDTO dto) {
        return produtoService.cadastrar(dto);
    }

    @GetMapping
    public List<Produto> listarTodos() {
        return produtoService.listarTodos();
    }

    @GetMapping("/ativos")
    public List<Produto> listarAtivos() {
        return produtoService.listarAtivos();
    }

    @GetMapping("/{id}")
    public Produto buscarPorId(@PathVariable Long id) {
        return produtoService.buscarPorId(id);
    }

    @GetMapping("/buscar/nome")
    public List<Produto> buscarPorNome(@RequestParam String nome) {
        return produtoService.buscarPorNome(nome);
    }

    @GetMapping("/categoria/{categoriaId}")
    public List<Produto> buscarPorCategoria(@PathVariable Long categoriaId) {
        return produtoService.buscarPorCategoria(categoriaId);
    }

    @GetMapping("/categoria/{categoriaId}/ativos")
    public List<Produto> buscarAtivosPorCategoria(@PathVariable Long categoriaId) {
        return produtoService.buscarAtivosPorCategoria(categoriaId);
    }

    @GetMapping("/estado")
    public List<Produto> buscarPorEstadoConservacao(
            @RequestParam EstadoConservacao estadoConservacao
    ) {
        return produtoService.buscarPorEstadoConservacao(estadoConservacao);
    }

    @GetMapping("/preco")
    public List<Produto> buscarPorFaixaPreco(
            @RequestParam BigDecimal minimo,
            @RequestParam BigDecimal maximo
    ) {
        return produtoService.buscarPorFaixaPreco(minimo, maximo);
    }

    @PutMapping("/{id}")
    public Produto atualizar(
            @PathVariable Long id,
            @RequestBody @Valid ProdutoAtualizarDTO dto
    ) {
        return produtoService.atualizar(id, dto);
    }

    @PutMapping("/{id}/inativar")
    public Produto inativar(@PathVariable Long id) {
        return produtoService.inativar(id);
    }

    @PutMapping("/{id}/reativar")
    public Produto reativar(@PathVariable Long id) {
        return produtoService.reativar(id);
    }
}