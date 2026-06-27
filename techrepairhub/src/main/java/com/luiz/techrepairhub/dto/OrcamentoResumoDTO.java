package com.luiz.techrepairhub.dto;

import com.luiz.techrepairhub.entity.StatusOrcamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrcamentoResumoDTO {

    private Long id;
    private Long ordemServicoId;
    private String cliente;
    private String equipamento;
    private String descricao;
    private BigDecimal valorMaoObra;
    private BigDecimal valorPecas;
    private BigDecimal valorTotal;
    private StatusOrcamento status;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataResposta;

    public OrcamentoResumoDTO(
            Long id,
            Long ordemServicoId,
            String cliente,
            String equipamento,
            String descricao,
            BigDecimal valorMaoObra,
            BigDecimal valorPecas,
            BigDecimal valorTotal,
            StatusOrcamento status,
            LocalDateTime dataCriacao,
            LocalDateTime dataResposta
    ) {
        this.id = id;
        this.ordemServicoId = ordemServicoId;
        this.cliente = cliente;
        this.equipamento = equipamento;
        this.descricao = descricao;
        this.valorMaoObra = valorMaoObra;
        this.valorPecas = valorPecas;
        this.valorTotal = valorTotal;
        this.status = status;
        this.dataCriacao = dataCriacao;
        this.dataResposta = dataResposta;
    }

    public Long getId() {
        return id;
    }

    public Long getOrdemServicoId() {
        return ordemServicoId;
    }

    public String getCliente() {
        return cliente;
    }

    public String getEquipamento() {
        return equipamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getValorMaoObra() {
        return valorMaoObra;
    }

    public BigDecimal getValorPecas() {
        return valorPecas;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public StatusOrcamento getStatus() {
        return status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataResposta() {
        return dataResposta;
    }
}
