package com.luiz.techrepairhub.controller;

import com.luiz.techrepairhub.dto.TecnicoAtualizarDTO;
import com.luiz.techrepairhub.dto.TecnicoCadastroDTO;
import com.luiz.techrepairhub.entity.Tecnico;
import com.luiz.techrepairhub.service.TecnicoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tecnicos")
public class TecnicoController {

    private final TecnicoService tecnicoService;

    public TecnicoController(TecnicoService tecnicoService) {
        this.tecnicoService = tecnicoService;
    }

    @PostMapping
    public Tecnico cadastrar(@RequestBody @Valid TecnicoCadastroDTO dto) {
        return tecnicoService.cadastrar(dto);
    }

    @GetMapping
    public List<Tecnico> listarTodos() {
        return tecnicoService.listarTodos();
    }

    @GetMapping("/ativos")
    public List<Tecnico> listarAtivos() {
        return tecnicoService.listarAtivos();
    }

    @GetMapping("/{id}")
    public Tecnico buscarPorId(@PathVariable Long id) {
        return tecnicoService.buscarPorId(id);
    }

    @GetMapping("/usuario/{usuarioId}")
    public Tecnico buscarPorUsuarioId(@PathVariable Long usuarioId) {
        return tecnicoService.buscarPorUsuarioId(usuarioId);
    }

    @PutMapping("/{id}/especialidade")
    public Tecnico atualizarEspecialidade(
            @PathVariable Long id,
            @RequestBody @Valid TecnicoAtualizarDTO dto
    ) {
        return tecnicoService.atualizarEspecialidade(id, dto.getEspecialidade());
    }

    @PutMapping("/{id}/inativar")
    public Tecnico inativar(@PathVariable Long id) {
        return tecnicoService.inativar(id);
    }
}