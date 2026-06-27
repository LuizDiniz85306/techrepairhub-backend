package com.luiz.techrepairhub.controller;

import com.luiz.techrepairhub.dto.EspecificacaoProdutoAtualizarDTO;
import com.luiz.techrepairhub.dto.EspecificacaoProdutoCadastroDTO;
import com.luiz.techrepairhub.entity.EspecificacaoProduto;
import com.luiz.techrepairhub.service.EspecificacaoProdutoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/especificacoes-produto")
public class EspecificacaoProdutoController {

    private final EspecificacaoProdutoService especificacaoProdutoService;

    public EspecificacaoProdutoController(EspecificacaoProdutoService especificacaoProdutoService) {
        this.especificacaoProdutoService = especificacaoProdutoService;
    }

    @PostMapping
    public EspecificacaoProduto cadastrar(@RequestBody @Valid EspecificacaoProdutoCadastroDTO dto) {
        return especificacaoProdutoService.cadastrar(dto);
    }

    @GetMapping
    public List<EspecificacaoProduto> listarTodas() {
        return especificacaoProdutoService.listarTodas();
    }

    @GetMapping("/{id}")
    public EspecificacaoProduto buscarPorId(@PathVariable Long id) {
        return especificacaoProdutoService.buscarPorId(id);
    }

    @GetMapping("/produto/{produtoId}")
    public List<EspecificacaoProduto> listarPorProduto(@PathVariable Long produtoId) {
        return especificacaoProdutoService.listarPorProduto(produtoId);
    }

    @GetMapping("/produto/{produtoId}/ativas")
    public List<EspecificacaoProduto> listarAtivasPorProduto(@PathVariable Long produtoId) {
        return especificacaoProdutoService.listarAtivasPorProduto(produtoId);
    }

    @GetMapping("/buscar")
    public List<EspecificacaoProduto> buscarPorNome(@RequestParam String nome) {
        return especificacaoProdutoService.buscarPorNome(nome);
    }

    @GetMapping("/produto/{produtoId}/buscar")
    public List<EspecificacaoProduto> buscarPorProdutoENome(
            @PathVariable Long produtoId,
            @RequestParam String nome
    ) {
        return especificacaoProdutoService.buscarPorProdutoENome(produtoId, nome);
    }

    @PutMapping("/{id}")
    public EspecificacaoProduto atualizar(
            @PathVariable Long id,
            @RequestBody @Valid EspecificacaoProdutoAtualizarDTO dto
    ) {
        return especificacaoProdutoService.atualizar(id, dto);
    }

    @PutMapping("/{id}/inativar")
    public EspecificacaoProduto inativar(@PathVariable Long id) {
        return especificacaoProdutoService.inativar(id);
    }

    @PutMapping("/{id}/reativar")
    public EspecificacaoProduto reativar(@PathVariable Long id) {
        return especificacaoProdutoService.reativar(id);
    }
}