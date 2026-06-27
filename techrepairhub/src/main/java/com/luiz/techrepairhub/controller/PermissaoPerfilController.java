package com.luiz.techrepairhub.controller;

import com.luiz.techrepairhub.dto.PermissaoPerfilDTO;
import com.luiz.techrepairhub.entity.PerfilUsuario;
import com.luiz.techrepairhub.service.PermissaoPerfilService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissoes")
public class PermissaoPerfilController {

    private final PermissaoPerfilService permissaoPerfilService;

    public PermissaoPerfilController(PermissaoPerfilService permissaoPerfilService) {
        this.permissaoPerfilService = permissaoPerfilService;
    }

    @GetMapping
    public List<PermissaoPerfilDTO> listarTodas() {
        return permissaoPerfilService.listarTodas();
    }

    @GetMapping("/perfil/{perfil}")
    public PermissaoPerfilDTO buscarPorPerfil(@PathVariable PerfilUsuario perfil) {
        return permissaoPerfilService.buscarPermissoesPorPerfil(perfil);
    }
}