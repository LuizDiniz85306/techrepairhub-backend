package com.luiz.techrepairhub.controller;

import com.luiz.techrepairhub.dto.*;
import com.luiz.techrepairhub.service.PecaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pecas")
public class PecaController {

    private final PecaService pecaService;

    public PecaController(PecaService pecaService) {
        this.pecaService = pecaService;
    }

    @PostMapping
    public PecaResumoDTO cadastrar(@RequestBody @Valid PecaCadastroDTO dto) {
        return pecaService.cadastrar(dto);
    }

    @GetMapping
    public List<PecaResumoDTO> listarTodas() {
        return pecaService.listarTodas();
    }

    @GetMapping("/ativas")
    public List<PecaResumoDTO> listarAtivas() {
        return pecaService.listarAtivas();
    }

    @GetMapping("/{id}")
    public PecaResumoDTO buscarPorId(@PathVariable Long id) {
        return pecaService.buscarPorId(id);
    }

    @GetMapping("/buscar")
    public List<PecaResumoDTO> buscarPorNome(@RequestParam String nome) {
        return pecaService.buscarPorNome(nome);
    }

    @GetMapping("/estoque-baixo")
    public List<PecaResumoDTO> listarEstoqueBaixo() {
        return pecaService.listarEstoqueBaixo();
    }

    @PutMapping("/{id}")
    public PecaResumoDTO atualizar(
            @PathVariable Long id,
            @RequestBody @Valid PecaAtualizarDTO dto
    ) {
        return pecaService.atualizar(id, dto);
    }

    @PutMapping("/{id}/inativar")
    public PecaResumoDTO inativar(@PathVariable Long id) {
        return pecaService.inativar(id);
    }

    @PutMapping("/{id}/reativar")
    public PecaResumoDTO reativar(@PathVariable Long id) {
        return pecaService.reativar(id);
    }

    @PostMapping("/{id}/movimentar-estoque")
    public PecaResumoDTO movimentarEstoque(
            @PathVariable Long id,
            @RequestBody @Valid MovimentacaoPecaDTO dto
    ) {
        return pecaService.movimentarEstoque(id, dto);
    }

    @GetMapping("/{id}/movimentacoes")
    public List<MovimentacaoPecaResumoDTO> listarMovimentacoes(@PathVariable Long id) {
        return pecaService.listarMovimentacoes(id);
    }
}
