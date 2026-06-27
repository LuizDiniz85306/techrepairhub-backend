package com.luiz.techrepairhub.dto;

import com.luiz.techrepairhub.entity.FormaPagamento;
import com.luiz.techrepairhub.entity.TipoDespesa;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DespesaResumoDTO {

    private Long id;
    private String descricao;
    private BigDecimal valor;
    private TipoDespesa tipoDespesa;
    private FormaPagamento formaPagamento;
    private LocalDateTime dataDespesa;
    private Boolean paga;
    private Boolean ativa;
    private String observacao;

    public DespesaResumoDTO(
            Long id,
            String descricao,
            BigDecimal valor,
            TipoDespesa tipoDespesa,
            FormaPagamento formaPagamento,
            LocalDateTime dataDespesa,
            Boolean paga,
            Boolean ativa,
            String observacao
    ) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.tipoDespesa = tipoDespesa;
        this.formaPagamento = formaPagamento;
        this.dataDespesa = dataDespesa;
        this.paga = paga;
        this.ativa = ativa;
        this.observacao = observacao;
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public TipoDespesa getTipoDespesa() {
        return tipoDespesa;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public LocalDateTime getDataDespesa() {
        return dataDespesa;
    }

    public Boolean getPaga() {
        return paga;
    }

    public Boolean getAtiva() {
        return ativa;
    }

    public String getObservacao() {
        return observacao;
    }
}