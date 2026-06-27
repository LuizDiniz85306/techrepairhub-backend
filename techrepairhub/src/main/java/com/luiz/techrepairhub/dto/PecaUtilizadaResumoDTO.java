package com.luiz.techrepairhub.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PecaUtilizadaResumoDTO {

    private Long id;
    private Long ordemServicoId;
    private Long pecaId;
    private String peca;
    private Integer quantidade;
    private BigDecimal valorUnitario;
    private BigDecimal subtotal;
    private LocalDateTime dataUtilizacao;

    public PecaUtilizadaResumoDTO(
            Long id,
            Long ordemServicoId,
            Long pecaId,
            String peca,
            Integer quantidade,
            BigDecimal valorUnitario,
            BigDecimal subtotal,
            LocalDateTime dataUtilizacao
    ) {
        this.id = id;
        this.ordemServicoId = ordemServicoId;
        this.pecaId = pecaId;
        this.peca = peca;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.subtotal = subtotal;
        this.dataUtilizacao = dataUtilizacao;
    }

    public Long getId() {
        return id;
    }

    public Long getOrdemServicoId() {
        return ordemServicoId;
    }

    public Long getPecaId() {
        return pecaId;
    }

    public String getPeca() {
        return peca;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public LocalDateTime getDataUtilizacao() {
        return dataUtilizacao;
    }
}