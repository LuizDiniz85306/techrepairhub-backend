package com.luiz.techrepairhub.dto;

import com.luiz.techrepairhub.entity.ResultadoFluxoCaixa;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DashboardResumoDTO {

    private LocalDateTime inicio;
    private LocalDateTime fim;

    private Long totalClientes;
    private Long totalTecnicos;
    private Long totalProdutos;
    private Long totalPedidos;

    private Long totalOrdensServico;
    private Long osAbertas;
    private Long osEmAnalise;
    private Long osAguardandoOrcamento;
    private Long osAguardandoAprovacao;
    private Long osEmExecucao;
    private Long osFinalizadas;
    private Long osCanceladas;

    private Long pecasComEstoqueBaixo;

    private BigDecimal totalReceitas;
    private BigDecimal totalDespesas;
    private BigDecimal saldoFinanceiro;
    private ResultadoFluxoCaixa resultadoFinanceiro;

    public DashboardResumoDTO(
            LocalDateTime inicio,
            LocalDateTime fim,
            Long totalClientes,
            Long totalTecnicos,
            Long totalProdutos,
            Long totalPedidos,
            Long totalOrdensServico,
            Long osAbertas,
            Long osEmAnalise,
            Long osAguardandoOrcamento,
            Long osAguardandoAprovacao,
            Long osEmExecucao,
            Long osFinalizadas,
            Long osCanceladas,
            Long pecasComEstoqueBaixo,
            BigDecimal totalReceitas,
            BigDecimal totalDespesas,
            BigDecimal saldoFinanceiro,
            ResultadoFluxoCaixa resultadoFinanceiro
    ) {
        this.inicio = inicio;
        this.fim = fim;
        this.totalClientes = totalClientes;
        this.totalTecnicos = totalTecnicos;
        this.totalProdutos = totalProdutos;
        this.totalPedidos = totalPedidos;
        this.totalOrdensServico = totalOrdensServico;
        this.osAbertas = osAbertas;
        this.osEmAnalise = osEmAnalise;
        this.osAguardandoOrcamento = osAguardandoOrcamento;
        this.osAguardandoAprovacao = osAguardandoAprovacao;
        this.osEmExecucao = osEmExecucao;
        this.osFinalizadas = osFinalizadas;
        this.osCanceladas = osCanceladas;
        this.pecasComEstoqueBaixo = pecasComEstoqueBaixo;
        this.totalReceitas = totalReceitas;
        this.totalDespesas = totalDespesas;
        this.saldoFinanceiro = saldoFinanceiro;
        this.resultadoFinanceiro = resultadoFinanceiro;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public LocalDateTime getFim() {
        return fim;
    }

    public Long getTotalClientes() {
        return totalClientes;
    }

    public Long getTotalTecnicos() {
        return totalTecnicos;
    }

    public Long getTotalProdutos() {
        return totalProdutos;
    }

    public Long getTotalPedidos() {
        return totalPedidos;
    }

    public Long getTotalOrdensServico() {
        return totalOrdensServico;
    }

    public Long getOsAbertas() {
        return osAbertas;
    }

    public Long getOsEmAnalise() {
        return osEmAnalise;
    }

    public Long getOsAguardandoOrcamento() {
        return osAguardandoOrcamento;
    }

    public Long getOsAguardandoAprovacao() {
        return osAguardandoAprovacao;
    }

    public Long getOsEmExecucao() {
        return osEmExecucao;
    }

    public Long getOsFinalizadas() {
        return osFinalizadas;
    }

    public Long getOsCanceladas() {
        return osCanceladas;
    }

    public Long getPecasComEstoqueBaixo() {
        return pecasComEstoqueBaixo;
    }

    public BigDecimal getTotalReceitas() {
        return totalReceitas;
    }

    public BigDecimal getTotalDespesas() {
        return totalDespesas;
    }

    public BigDecimal getSaldoFinanceiro() {
        return saldoFinanceiro;
    }

    public ResultadoFluxoCaixa getResultadoFinanceiro() {
        return resultadoFinanceiro;
    }
}
