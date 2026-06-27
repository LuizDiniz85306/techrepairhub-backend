package com.luiz.techrepairhub.controller;

import com.luiz.techrepairhub.dto.DespesaAtualizarDTO;
import com.luiz.techrepairhub.dto.DespesaCadastroDTO;
import com.luiz.techrepairhub.dto.DespesaResumoDTO;
import com.luiz.techrepairhub.entity.TipoDespesa;
import com.luiz.techrepairhub.service.DespesaService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/despesas")
public class DespesaController {

    private final DespesaService despesaService;

    public DespesaController(DespesaService despesaService) {
        this.despesaService = despesaService;
    }

    @PostMapping
    public DespesaResumoDTO cadastrar(@RequestBody @Valid DespesaCadastroDTO dto) {
        return despesaService.cadastrar(dto);
    }

    @GetMapping
    public List<DespesaResumoDTO> listarTodas() {
        return despesaService.listarTodas();
    }

    @GetMapping("/ativas")
    public List<DespesaResumoDTO> listarAtivas() {
        return despesaService.listarAtivas();
    }

    @GetMapping("/{id}")
    public DespesaResumoDTO buscarPorId(@PathVariable Long id) {
        return despesaService.buscarPorId(id);
    }

    @GetMapping("/tipo/{tipoDespesa}")
    public List<DespesaResumoDTO> listarPorTipo(@PathVariable TipoDespesa tipoDespesa) {
        return despesaService.listarPorTipo(tipoDespesa);
    }

    @GetMapping("/pagamento/{paga}")
    public List<DespesaResumoDTO> listarPorStatusPagamento(@PathVariable Boolean paga) {
        return despesaService.listarPorStatusPagamento(paga);
    }

    @GetMapping("/periodo")
    public List<DespesaResumoDTO> listarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim
    ) {
        return despesaService.listarPorPeriodo(inicio, fim);
    }

    @PutMapping("/{id}")
    public DespesaResumoDTO atualizar(
            @PathVariable Long id,
            @RequestBody @Valid DespesaAtualizarDTO dto
    ) {
        return despesaService.atualizar(id, dto);
    }

    @PutMapping("/{id}/marcar-paga")
    public DespesaResumoDTO marcarComoPaga(@PathVariable Long id) {
        return despesaService.marcarComoPaga(id);
    }

    @PutMapping("/{id}/marcar-pendente")
    public DespesaResumoDTO marcarComoPendente(@PathVariable Long id) {
        return despesaService.marcarComoPendente(id);
    }

    @PutMapping("/{id}/inativar")
    public DespesaResumoDTO inativar(@PathVariable Long id) {
        return despesaService.inativar(id);
    }

    @PutMapping("/{id}/reativar")
    public DespesaResumoDTO reativar(@PathVariable Long id) {
        return despesaService.reativar(id);
    }
}