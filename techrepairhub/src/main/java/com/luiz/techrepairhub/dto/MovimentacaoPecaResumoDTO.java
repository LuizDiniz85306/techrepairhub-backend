package com.luiz.techrepairhub.dto;

import com.luiz.techrepairhub.entity.TipoMovimentacaoPeca;

import java.time.LocalDateTime;

public class MovimentacaoPecaResumoDTO {

    private Long id;
    private Long pecaId;
    private String peca;
    private TipoMovimentacaoPeca tipoMovimentacao;
    private Integer quantidade;
    private Integer quantidadeAnterior;
    private Integer quantidadeNova;
    private LocalDateTime dataMovimentacao;
    private String observacao;

    public MovimentacaoPecaResumoDTO(
            Long id,
            Long pecaId,
            String peca,
            TipoMovimentacaoPeca tipoMovimentacao,
            Integer quantidade,
            Integer quantidadeAnterior,
            Integer quantidadeNova,
            LocalDateTime dataMovimentacao,
            String observacao
    ) {
        this.id = id;
        this.pecaId = pecaId;
        this.peca = peca;
        this.tipoMovimentacao = tipoMovimentacao;
        this.quantidade = quantidade;
        this.quantidadeAnterior = quantidadeAnterior;
        this.quantidadeNova = quantidadeNova;
        this.dataMovimentacao = dataMovimentacao;
        this.observacao = observacao;
    }

    public Long getId() {
        return id;
    }

    public Long getPecaId() {
        return pecaId;
    }

    public String getPeca() {
        return peca;
    }

    public TipoMovimentacaoPeca getTipoMovimentacao() {
        return tipoMovimentacao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public Integer getQuantidadeAnterior() {
        return quantidadeAnterior;
    }

    public Integer getQuantidadeNova() {
        return quantidadeNova;
    }

    public LocalDateTime getDataMovimentacao() {
        return dataMovimentacao;
    }

    public String getObservacao() {
        return observacao;
    }
}