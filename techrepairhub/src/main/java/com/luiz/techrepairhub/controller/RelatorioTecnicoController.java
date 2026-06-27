package com.luiz.techrepairhub.controller;

import com.luiz.techrepairhub.dto.RelatorioTecnicoAtualizarDTO;
import com.luiz.techrepairhub.dto.RelatorioTecnicoCadastroDTO;
import com.luiz.techrepairhub.dto.RelatorioTecnicoResumoDTO;
import com.luiz.techrepairhub.service.RelatorioTecnicoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/relatorios-tecnicos")
public class RelatorioTecnicoController {

    private final RelatorioTecnicoService relatorioTecnicoService;

    public RelatorioTecnicoController(RelatorioTecnicoService relatorioTecnicoService) {
        this.relatorioTecnicoService = relatorioTecnicoService;
    }

    @PostMapping
    public RelatorioTecnicoResumoDTO criar(@RequestBody @Valid RelatorioTecnicoCadastroDTO dto) {
        return relatorioTecnicoService.criar(dto);
    }

    @GetMapping
    public List<RelatorioTecnicoResumoDTO> listarTodos() {
        return relatorioTecnicoService.listarTodos();
    }

    @GetMapping("/{id}")
    public RelatorioTecnicoResumoDTO buscarPorId(@PathVariable Long id) {
        return relatorioTecnicoService.buscarPorId(id);
    }

    @GetMapping("/ordem-servico/{ordemServicoId}")
    public RelatorioTecnicoResumoDTO buscarPorOrdemServico(@PathVariable Long ordemServicoId) {
        return relatorioTecnicoService.buscarPorOrdemServico(ordemServicoId);
    }

    @GetMapping("/tecnico/{tecnicoId}")
    public List<RelatorioTecnicoResumoDTO> listarPorTecnico(@PathVariable Long tecnicoId) {
        return relatorioTecnicoService.listarPorTecnico(tecnicoId);
    }

    @PutMapping("/{id}")
    public RelatorioTecnicoResumoDTO atualizar(
            @PathVariable Long id,
            @RequestBody @Valid RelatorioTecnicoAtualizarDTO dto
    ) {
        return relatorioTecnicoService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        relatorioTecnicoService.remover(id);
    }
}