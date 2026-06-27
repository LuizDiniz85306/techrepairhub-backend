package com.luiz.techrepairhub.controller;

import com.luiz.techrepairhub.dto.UsuarioCadastroDTO;
import com.luiz.techrepairhub.entity.Usuario;
import com.luiz.techrepairhub.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public Usuario cadastrar(@RequestBody @Valid UsuarioCadastroDTO dto) {
        return usuarioService.cadastrar(dto);
    }

    @GetMapping
    public List<Usuario> listarTodos() {
        return usuarioService.listarTodos();
    }
    
    @GetMapping("/{id}")
    public Usuario buscarPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id);
    }

    @PutMapping("/{id}/inativar")
    public Usuario inativar(@PathVariable Long id) {
        return usuarioService.inativar(id);
    }
}