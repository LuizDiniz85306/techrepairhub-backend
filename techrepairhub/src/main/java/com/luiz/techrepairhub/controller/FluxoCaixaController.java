package com.luiz.techrepairhub.controller;

import com.luiz.techrepairhub.dto.FluxoCaixaResumoDTO;
import com.luiz.techrepairhub.service.FluxoCaixaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/fluxo-caixa")
public class FluxoCaixaController {

    private final FluxoCaixaService fluxoCaixaService;

    public FluxoCaixaController(FluxoCaixaService fluxoCaixaService) {
        this.fluxoCaixaService = fluxoCaixaService;
    }

    @GetMapping
    public FluxoCaixaResumoDTO calcularFluxoCaixa(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim
    ) {
        return fluxoCaixaService.calcularFluxoCaixa(inicio, fim);
    }
}