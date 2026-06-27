package com.luiz.techrepairhub.controller;

import com.luiz.techrepairhub.dto.OrdemServicoAtualizarDTO;
import com.luiz.techrepairhub.dto.OrdemServicoCadastroDTO;
import com.luiz.techrepairhub.dto.OrdemServicoResumoDTO;
import com.luiz.techrepairhub.entity.StatusOrdemServico;
import com.luiz.techrepairhub.service.OrdemServicoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {

    private final OrdemServicoService ordemServicoService;

    public OrdemServicoController(OrdemServicoService ordemServicoService) {
        this.ordemServicoService = ordemServicoService;
    }

    @PostMapping
    public OrdemServicoResumoDTO abrir(@RequestBody @Valid OrdemServicoCadastroDTO dto) {
        return ordemServicoService.abrir(dto);
    }

    @GetMapping
    public List<OrdemServicoResumoDTO> listarTodas() {
        return ordemServicoService.listarTodas();
    }

    @GetMapping("/{id}")
    public OrdemServicoResumoDTO buscarPorId(@PathVariable Long id) {
        return ordemServicoService.buscarPorId(id);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<OrdemServicoResumoDTO> listarPorCliente(@PathVariable Long clienteId) {
        return ordemServicoService.listarPorCliente(clienteId);
    }

    @GetMapping("/equipamento/{equipamentoId}")
    public List<OrdemServicoResumoDTO> listarPorEquipamento(@PathVariable Long equipamentoId) {
        return ordemServicoService.listarPorEquipamento(equipamentoId);
    }

    @GetMapping("/tecnico/{tecnicoId}")
    public List<OrdemServicoResumoDTO> listarPorTecnico(@PathVariable Long tecnicoId) {
        return ordemServicoService.listarPorTecnico(tecnicoId);
    }

    @GetMapping("/status")
    public List<OrdemServicoResumoDTO> listarPorStatus(@RequestParam StatusOrdemServico status) {
        return ordemServicoService.listarPorStatus(status);
    }

    @PutMapping("/{id}")
    public OrdemServicoResumoDTO atualizar(
            @PathVariable Long id,
            @RequestBody @Valid OrdemServicoAtualizarDTO dto
    ) {
        return ordemServicoService.atualizar(id, dto);
    }

    @PutMapping("/{ordemServicoId}/atribuir-tecnico/{tecnicoId}")
    public OrdemServicoResumoDTO atribuirTecnico(
            @PathVariable Long ordemServicoId,
            @PathVariable Long tecnicoId
    ) {
        return ordemServicoService.atribuirTecnico(ordemServicoId, tecnicoId);
    }

    @PutMapping("/{id}/finalizar")
    public OrdemServicoResumoDTO finalizar(@PathVariable Long id) {
        return ordemServicoService.finalizar(id);
    }

    @PutMapping("/{id}/cancelar")
    public OrdemServicoResumoDTO cancelar(@PathVariable Long id) {
        return ordemServicoService.cancelar(id);
    }
}