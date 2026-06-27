package com.luiz.techrepairhub.controller;

import com.luiz.techrepairhub.dto.MenuInternoDTO;
import com.luiz.techrepairhub.entity.PerfilUsuario;
import com.luiz.techrepairhub.service.AreaInternaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/area-interna")
public class AreaInternaController {

    private final AreaInternaService areaInternaService;

    public AreaInternaController(AreaInternaService areaInternaService) {
        this.areaInternaService = areaInternaService;
    }

    @GetMapping("/menu/{perfil}")
    public List<MenuInternoDTO> buscarMenuPorPerfil(@PathVariable PerfilUsuario perfil) {
        return areaInternaService.montarMenuPorPerfil(perfil);
    }
}