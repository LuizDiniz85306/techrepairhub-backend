package com.luiz.techrepairhub.controller;

import com.luiz.techrepairhub.dto.CategoriaAtualizarDTO;
import com.luiz.techrepairhub.dto.CategoriaCadastroDTO;
import com.luiz.techrepairhub.entity.Categoria;
import com.luiz.techrepairhub.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    public Categoria cadastrar(@RequestBody @Valid CategoriaCadastroDTO dto) {
        return categoriaService.cadastrar(dto);
    }

    @GetMapping
    public List<Categoria> listarTodos() {
        return categoriaService.listarTodos();
    }

    @GetMapping("/ativas")
    public List<Categoria> listarAtivas() {
        return categoriaService.listarAtivas();
    }

    @GetMapping("/{id}")
    public Categoria buscarPorId(@PathVariable Long id) {
        return categoriaService.buscarPorId(id);
    }

    @GetMapping("/buscar")
    public List<Categoria> buscarPorNome(@RequestParam String nome) {
        return categoriaService.buscarPorNome(nome);
    }

    @PutMapping("/{id}")
    public Categoria atualizar(
            @PathVariable Long id,
            @RequestBody @Valid CategoriaAtualizarDTO dto
    ) {
        return categoriaService.atualizar(id, dto);
    }

    @PutMapping("/{id}/inativar")
    public Categoria inativar(@PathVariable Long id) {
        return categoriaService.inativar(id);
    }

    @PutMapping("/{id}/reativar")
    public Categoria reativar(@PathVariable Long id) {
        return categoriaService.reativar(id);
    }
}