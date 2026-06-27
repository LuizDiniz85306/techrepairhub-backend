package com.luiz.techrepairhub.controller;

import com.luiz.techrepairhub.dto.ReceitaAtualizarDTO;
import com.luiz.techrepairhub.dto.ReceitaCadastroDTO;
import com.luiz.techrepairhub.dto.ReceitaResumoDTO;
import com.luiz.techrepairhub.entity.TipoReceita;
import com.luiz.techrepairhub.service.ReceitaService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {

    private final ReceitaService receitaService;

    public ReceitaController(ReceitaService receitaService) {
        this.receitaService = receitaService;
    }

    @PostMapping
    public ReceitaResumoDTO cadastrar(@RequestBody @Valid ReceitaCadastroDTO dto) {
        return receitaService.cadastrar(dto);
    }

    @GetMapping
    public List<ReceitaResumoDTO> listarTodas() {
        return receitaService.listarTodas();
    }

    @GetMapping("/ativas")
    public List<ReceitaResumoDTO> listarAtivas() {
        return receitaService.listarAtivas();
    }

    @GetMapping("/{id}")
    public ReceitaResumoDTO buscarPorId(@PathVariable Long id) {
        return receitaService.buscarPorId(id);
    }

    @GetMapping("/tipo/{tipoReceita}")
    public List<ReceitaResumoDTO> listarPorTipo(@PathVariable TipoReceita tipoReceita) {
        return receitaService.listarPorTipo(tipoReceita);
    }

    @GetMapping("/pedido/{pedidoId}")
    public List<ReceitaResumoDTO> listarPorPedido(@PathVariable Long pedidoId) {
        return receitaService.listarPorPedido(pedidoId);
    }

    @GetMapping("/ordem-servico/{ordemServicoId}")
    public List<ReceitaResumoDTO> listarPorOrdemServico(@PathVariable Long ordemServicoId) {
        return receitaService.listarPorOrdemServico(ordemServicoId);
    }

    @GetMapping("/periodo")
    public List<ReceitaResumoDTO> listarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim
    ) {
        return receitaService.listarPorPeriodo(inicio, fim);
    }

    @PutMapping("/{id}")
    public ReceitaResumoDTO atualizar(
            @PathVariable Long id,
            @RequestBody @Valid ReceitaAtualizarDTO dto
    ) {
        return receitaService.atualizar(id, dto);
    }

    @PutMapping("/{id}/inativar")
    public ReceitaResumoDTO inativar(@PathVariable Long id) {
        return receitaService.inativar(id);
    }

    @PutMapping("/{id}/reativar")
    public ReceitaResumoDTO reativar(@PathVariable Long id) {
        return receitaService.reativar(id);
    }
}