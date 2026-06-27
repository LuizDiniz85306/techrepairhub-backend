package com.luiz.techrepairhub.controller;

import com.luiz.techrepairhub.dto.ImagemProdutoAtualizarDTO;
import com.luiz.techrepairhub.dto.ImagemProdutoCadastroDTO;
import com.luiz.techrepairhub.entity.ImagemProduto;
import com.luiz.techrepairhub.service.ImagemProdutoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/imagens-produto")
public class ImagemProdutoController {

    private final ImagemProdutoService imagemProdutoService;

    public ImagemProdutoController(ImagemProdutoService imagemProdutoService) {
        this.imagemProdutoService = imagemProdutoService;
    }

    @PostMapping
    public ImagemProduto cadastrar(@RequestBody @Valid ImagemProdutoCadastroDTO dto) {
        return imagemProdutoService.cadastrar(dto);
    }

    @GetMapping
    public List<ImagemProduto> listarTodas() {
        return imagemProdutoService.listarTodas();
    }

    @GetMapping("/{id}")
    public ImagemProduto buscarPorId(@PathVariable Long id) {
        return imagemProdutoService.buscarPorId(id);
    }

    @GetMapping("/produto/{produtoId}")
    public List<ImagemProduto> listarPorProduto(@PathVariable Long produtoId) {
        return imagemProdutoService.listarPorProduto(produtoId);
    }

    @GetMapping("/produto/{produtoId}/ativas")
    public List<ImagemProduto> listarAtivasPorProduto(@PathVariable Long produtoId) {
        return imagemProdutoService.listarAtivasPorProduto(produtoId);
    }

    @GetMapping("/produto/{produtoId}/principal")
    public ImagemProduto buscarImagemPrincipal(@PathVariable Long produtoId) {
        return imagemProdutoService.buscarImagemPrincipal(produtoId);
    }

    @PutMapping("/{id}")
    public ImagemProduto atualizar(
            @PathVariable Long id,
            @RequestBody @Valid ImagemProdutoAtualizarDTO dto
    ) {
        return imagemProdutoService.atualizar(id, dto);
    }

    @PutMapping("/{id}/principal")
    public ImagemProduto definirComoPrincipal(@PathVariable Long id) {
        return imagemProdutoService.definirComoPrincipal(id);
    }

    @PutMapping("/{id}/inativar")
    public ImagemProduto inativar(@PathVariable Long id) {
        return imagemProdutoService.inativar(id);
    }

    @PutMapping("/{id}/reativar")
    public ImagemProduto reativar(@PathVariable Long id) {
        return imagemProdutoService.reativar(id);
    }
}
