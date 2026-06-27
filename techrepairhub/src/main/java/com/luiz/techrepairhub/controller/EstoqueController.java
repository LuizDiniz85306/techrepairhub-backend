package com.luiz.techrepairhub.controller;

import com.luiz.techrepairhub.dto.EstoqueAtualizarDTO;
import com.luiz.techrepairhub.dto.EstoqueCadastroDTO;
import com.luiz.techrepairhub.entity.Estoque;
import com.luiz.techrepairhub.service.EstoqueService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estoques")
public class EstoqueController {

    private final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @PostMapping
    public Estoque cadastrar(@RequestBody @Valid EstoqueCadastroDTO dto) {
        return estoqueService.cadastrar(dto);
    }

    @GetMapping
    public List<Estoque> listarTodos() {
        return estoqueService.listarTodos();
    }

    @GetMapping("/ativos")
    public List<Estoque> listarAtivos() {
        return estoqueService.listarAtivos();
    }

    @GetMapping("/{id}")
    public Estoque buscarPorId(@PathVariable Long id) {
        return estoqueService.buscarPorId(id);
    }

    @GetMapping("/produto/{produtoId}")
    public Estoque buscarPorProdutoId(@PathVariable Long produtoId) {
        return estoqueService.buscarPorProdutoId(produtoId);
    }

    @GetMapping("/disponiveis")
    public List<Estoque> listarComEstoqueDisponivel() {
        return estoqueService.listarComEstoqueDisponivel();
    }

    @GetMapping("/sem-estoque")
    public List<Estoque> listarSemEstoque() {
        return estoqueService.listarSemEstoque();
    }

    @GetMapping("/abaixo-minimo")
    public List<Estoque> listarAbaixoDoMinimo() {
        return estoqueService.listarAbaixoDoMinimo();
    }

    @PutMapping("/{id}")
    public Estoque atualizar(
            @PathVariable Long id,
            @RequestBody @Valid EstoqueAtualizarDTO dto
    ) {
        return estoqueService.atualizar(id, dto);
    }

    @PutMapping("/produto/{produtoId}")
    public Estoque atualizarPorProduto(
            @PathVariable Long produtoId,
            @RequestBody @Valid EstoqueAtualizarDTO dto
    ) {
        return estoqueService.atualizarPorProduto(produtoId, dto);
    }

    @PutMapping("/{id}/inativar")
    public Estoque inativar(@PathVariable Long id) {
        return estoqueService.inativar(id);
    }

    @PutMapping("/{id}/reativar")
    public Estoque reativar(@PathVariable Long id) {
        return estoqueService.reativar(id);
    }
}