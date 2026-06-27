package com.luiz.techrepairhub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "despesas")
public class Despesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A descrição da despesa é obrigatória")
    @Column(nullable = false)
    private String descricao;

    @NotNull(message = "O valor da despesa é obrigatório")
    @DecimalMin(value = "0.01", message = "O valor da despesa deve ser maior que zero")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @NotNull(message = "O tipo da despesa é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_despesa", nullable = false)
    private TipoDespesa tipoDespesa;

    @NotNull(message = "A forma de pagamento é obrigatória")
    @Enumerated(EnumType.STRING)
    @Column(name = "forma_pagamento", nullable = false)
    private FormaPagamento formaPagamento;

    @Column(name = "data_despesa", nullable = false)
    private LocalDateTime dataDespesa = LocalDateTime.now();

    @Column(nullable = false)
    private Boolean paga = true;

    @Column(nullable = false)
    private Boolean ativa = true;

    @Column(columnDefinition = "TEXT")
    private String observacao;

    public Despesa() {
    }

    public Despesa(
            String descricao,
            BigDecimal valor,
            TipoDespesa tipoDespesa,
            FormaPagamento formaPagamento,
            Boolean paga,
            String observacao
    ) {
        this.descricao = descricao;
        this.valor = valor;
        this.tipoDespesa = tipoDespesa;
        this.formaPagamento = formaPagamento;
        this.paga = paga != null ? paga : true;
        this.observacao = observacao;
        this.ativa = true;
        this.dataDespesa = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public TipoDespesa getTipoDespesa() {
        return tipoDespesa;
    }

    public void setTipoDespesa(TipoDespesa tipoDespesa) {
        this.tipoDespesa = tipoDespesa;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public LocalDateTime getDataDespesa() {
        return dataDespesa;
    }

    public void setDataDespesa(LocalDateTime dataDespesa) {
        this.dataDespesa = dataDespesa;
    }

    public Boolean getPaga() {
        return paga;
    }

    public void setPaga(Boolean paga) {
        this.paga = paga;
    }

    public Boolean getAtiva() {
        return ativa;
    }

    public void setAtiva(Boolean ativa) {
        this.ativa = ativa;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}