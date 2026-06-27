package com.luiz.techrepairhub.controller;

import com.luiz.techrepairhub.dto.OrcamentoCadastroDTO;
import com.luiz.techrepairhub.dto.OrcamentoResumoDTO;
import com.luiz.techrepairhub.entity.StatusOrcamento;
import com.luiz.techrepairhub.service.OrcamentoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orcamentos")
public class OrcamentoController {

    private final OrcamentoService orcamentoService;

    public OrcamentoController(OrcamentoService orcamentoService) {
        this.orcamentoService = orcamentoService;
    }

    @PostMapping
    public OrcamentoResumoDTO criar(@RequestBody @Valid OrcamentoCadastroDTO dto) {
        return orcamentoService.criar(dto);
    }

    @GetMapping
    public List<OrcamentoResumoDTO> listarTodos() {
        return orcamentoService.listarTodos();
    }

    @GetMapping("/{id}")
    public OrcamentoResumoDTO buscarPorId(@PathVariable Long id) {
        return orcamentoService.buscarPorId(id);
    }

    @GetMapping("/ordem-servico/{ordemServicoId}")
    public List<OrcamentoResumoDTO> listarPorOrdemServico(@PathVariable Long ordemServicoId) {
        return orcamentoService.listarPorOrdemServico(ordemServicoId);
    }

    @GetMapping("/status")
    public List<OrcamentoResumoDTO> listarPorStatus(@RequestParam StatusOrcamento status) {
        return orcamentoService.listarPorStatus(status);
    }

    @PutMapping("/{id}/aprovar")
    public OrcamentoResumoDTO aprovar(@PathVariable Long id) {
        return orcamentoService.aprovar(id);
    }

    @PutMapping("/{id}/recusar")
    public OrcamentoResumoDTO recusar(@PathVariable Long id) {
        return orcamentoService.recusar(id);
    }
}