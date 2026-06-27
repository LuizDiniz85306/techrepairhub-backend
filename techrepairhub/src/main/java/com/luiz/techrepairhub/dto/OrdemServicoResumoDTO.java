package com.luiz.techrepairhub.dto;

import com.luiz.techrepairhub.entity.StatusOrdemServico;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrdemServicoResumoDTO {

    private Long ordemServicoId;
    private Long clienteId;
    private String nomeCliente;
    private Long equipamentoId;
    private String equipamento;
    private Long tecnicoId;
    private String nomeTecnico;
    private String descricaoProblema;
    private String diagnostico;
    private String solucaoAplicada;
    private StatusOrdemServico status;
    private BigDecimal valorTotal;
    private LocalDateTime dataAbertura;
    private LocalDateTime dataFinalizacao;

    public OrdemServicoResumoDTO(
            Long ordemServicoId,
            Long clienteId,
            String nomeCliente,
            Long equipamentoId,
            String equipamento,
            Long tecnicoId,
            String nomeTecnico,
            String descricaoProblema,
            String diagnostico,
            String solucaoAplicada,
            StatusOrdemServico status,
            BigDecimal valorTotal,
            LocalDateTime dataAbertura,
            LocalDateTime dataFinalizacao
    ) {
        this.ordemServicoId = ordemServicoId;
        this.clienteId = clienteId;
        this.nomeCliente = nomeCliente;
        this.equipamentoId = equipamentoId;
        this.equipamento = equipamento;
        this.tecnicoId = tecnicoId;
        this.nomeTecnico = nomeTecnico;
        this.descricaoProblema = descricaoProblema;
        this.diagnostico = diagnostico;
        this.solucaoAplicada = solucaoAplicada;
        this.status = status;
        this.valorTotal = valorTotal;
        this.dataAbertura = dataAbertura;
        this.dataFinalizacao = dataFinalizacao;
    }

    public Long getOrdemServicoId() {
        return ordemServicoId;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public Long getEquipamentoId() {
        return equipamentoId;
    }

    public String getEquipamento() {
        return equipamento;
    }

    public Long getTecnicoId() {
        return tecnicoId;
    }

    public String getNomeTecnico() {
        return nomeTecnico;
    }

    public String getDescricaoProblema() {
        return descricaoProblema;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public String getSolucaoAplicada() {
        return solucaoAplicada;
    }

    public StatusOrdemServico getStatus() {
        return status;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public LocalDateTime getDataFinalizacao() {
        return dataFinalizacao;
    }
}