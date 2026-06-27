package com.luiz.techrepairhub.dto;

import com.luiz.techrepairhub.entity.ResultadoFluxoCaixa;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FluxoCaixaResumoDTO {

    private LocalDateTime inicio;
    private LocalDateTime fim;
    private BigDecimal totalReceitas;
    private BigDecimal totalDespesas;
    private BigDecimal saldoFinal;
    private Integer quantidadeReceitas;
    private Integer quantidadeDespesas;
    private ResultadoFluxoCaixa resultado;

    public FluxoCaixaResumoDTO(
            LocalDateTime inicio,
            LocalDateTime fim,
            BigDecimal totalReceitas,
            BigDecimal totalDespesas,
            BigDecimal saldoFinal,
            Integer quantidadeReceitas,
            Integer quantidadeDespesas,
            ResultadoFluxoCaixa resultado
    ) {
        this.inicio = inicio;
        this.fim = fim;
        this.totalReceitas = totalReceitas;
        this.totalDespesas = totalDespesas;
        this.saldoFinal = saldoFinal;
        this.quantidadeReceitas = quantidadeReceitas;
        this.quantidadeDespesas = quantidadeDespesas;
        this.resultado = resultado;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public LocalDateTime getFim() {
        return fim;
    }

    public BigDecimal getTotalReceitas() {
        return totalReceitas;
    }

    public BigDecimal getTotalDespesas() {
        return totalDespesas;
    }

    public BigDecimal getSaldoFinal() {
        return saldoFinal;
    }

    public Integer getQuantidadeReceitas() {
        return quantidadeReceitas;
    }

    public Integer getQuantidadeDespesas() {
        return quantidadeDespesas;
    }

    public ResultadoFluxoCaixa getResultado() {
        return resultado;
    }
}