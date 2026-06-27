package com.luiz.techrepairhub.controller;

import com.luiz.techrepairhub.dto.HistoricoOrdemServicoCadastroDTO;
import com.luiz.techrepairhub.dto.HistoricoOrdemServicoResumoDTO;
import com.luiz.techrepairhub.entity.TipoHistoricoOrdemServico;
import com.luiz.techrepairhub.service.HistoricoOrdemServicoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historicos-ordem-servico")
public class HistoricoOrdemServicoController {

    private final HistoricoOrdemServicoService historicoService;

    public HistoricoOrdemServicoController(HistoricoOrdemServicoService historicoService) {
        this.historicoService = historicoService;
    }

    @PostMapping
    public HistoricoOrdemServicoResumoDTO registrarManual(
            @RequestBody @Valid HistoricoOrdemServicoCadastroDTO dto
    ) {
        return historicoService.registrarManual(dto);
    }

    @GetMapping
    public List<HistoricoOrdemServicoResumoDTO> listarTodos() {
        return historicoService.listarTodos();
    }

    @GetMapping("/{id}")
    public HistoricoOrdemServicoResumoDTO buscarPorId(@PathVariable Long id) {
        return historicoService.buscarPorId(id);
    }

    @GetMapping("/ordem-servico/{ordemServicoId}")
    public List<HistoricoOrdemServicoResumoDTO> listarPorOrdemServico(@PathVariable Long ordemServicoId) {
        return historicoService.listarPorOrdemServico(ordemServicoId);
    }

    @GetMapping("/ordem-servico/{ordemServicoId}/desc")
    public List<HistoricoOrdemServicoResumoDTO> listarPorOrdemServicoDesc(@PathVariable Long ordemServicoId) {
        return historicoService.listarPorOrdemServicoDesc(ordemServicoId);
    }

    @GetMapping("/tipo")
    public List<HistoricoOrdemServicoResumoDTO> listarPorTipo(@RequestParam TipoHistoricoOrdemServico tipo) {
        return historicoService.listarPorTipo(tipo);
    }
}