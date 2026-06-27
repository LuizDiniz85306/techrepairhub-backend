package com.luiz.techrepairhub.controller;

import com.luiz.techrepairhub.dto.PecaUtilizadaCadastroDTO;
import com.luiz.techrepairhub.dto.PecaUtilizadaResumoDTO;
import com.luiz.techrepairhub.service.PecaUtilizadaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pecas-utilizadas")
public class PecaUtilizadaController {

    private final PecaUtilizadaService pecaUtilizadaService;

    public PecaUtilizadaController(PecaUtilizadaService pecaUtilizadaService) {
        this.pecaUtilizadaService = pecaUtilizadaService;
    }

    @PostMapping
    public PecaUtilizadaResumoDTO adicionar(@RequestBody @Valid PecaUtilizadaCadastroDTO dto) {
        return pecaUtilizadaService.adicionar(dto);
    }

    @GetMapping
    public List<PecaUtilizadaResumoDTO> listarTodas() {
        return pecaUtilizadaService.listarTodas();
    }

    @GetMapping("/{id}")
    public PecaUtilizadaResumoDTO buscarPorId(@PathVariable Long id) {
        return pecaUtilizadaService.buscarPorId(id);
    }

    @GetMapping("/ordem-servico/{ordemServicoId}")
    public List<PecaUtilizadaResumoDTO> listarPorOrdemServico(@PathVariable Long ordemServicoId) {
        return pecaUtilizadaService.listarPorOrdemServico(ordemServicoId);
    }

    @GetMapping("/peca/{pecaId}")
    public List<PecaUtilizadaResumoDTO> listarPorPeca(@PathVariable Long pecaId) {
        return pecaUtilizadaService.listarPorPeca(pecaId);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        pecaUtilizadaService.remover(id);
    }
}