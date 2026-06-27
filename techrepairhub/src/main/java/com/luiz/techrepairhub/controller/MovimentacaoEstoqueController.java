package com.luiz.techrepairhub.controller;

import com.luiz.techrepairhub.dto.MovimentacaoEstoqueCadastroDTO;
import com.luiz.techrepairhub.entity.MovimentacaoEstoque;
import com.luiz.techrepairhub.entity.TipoMovimentacaoEstoque;
import com.luiz.techrepairhub.service.MovimentacaoEstoqueService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimentacoes-estoque")
public class MovimentacaoEstoqueController {

    private final MovimentacaoEstoqueService movimentacaoEstoqueService;

    public MovimentacaoEstoqueController(MovimentacaoEstoqueService movimentacaoEstoqueService) {
        this.movimentacaoEstoqueService = movimentacaoEstoqueService;
    }

    @PostMapping
    public MovimentacaoEstoque registrar(@RequestBody @Valid MovimentacaoEstoqueCadastroDTO dto) {
        return movimentacaoEstoqueService.registrar(dto);
    }

    @GetMapping
    public List<MovimentacaoEstoque> listarTodas() {
        return movimentacaoEstoqueService.listarTodas();
    }

    @GetMapping("/{id}")
    public MovimentacaoEstoque buscarPorId(@PathVariable Long id) {
        return movimentacaoEstoqueService.buscarPorId(id);
    }

    @GetMapping("/estoque/{estoqueId}")
    public List<MovimentacaoEstoque> listarPorEstoque(@PathVariable Long estoqueId) {
        return movimentacaoEstoqueService.listarPorEstoque(estoqueId);
    }

    @GetMapping("/produto/{produtoId}")
    public List<MovimentacaoEstoque> listarPorProduto(@PathVariable Long produtoId) {
        return movimentacaoEstoqueService.listarPorProduto(produtoId);
    }

    @GetMapping("/tipo")
    public List<MovimentacaoEstoque> listarPorTipo(@RequestParam TipoMovimentacaoEstoque tipo) {
        return movimentacaoEstoqueService.listarPorTipo(tipo);
    }
}