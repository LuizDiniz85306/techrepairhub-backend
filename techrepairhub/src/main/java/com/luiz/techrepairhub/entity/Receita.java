package com.luiz.techrepairhub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "receitas")
public class Receita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A descrição da receita é obrigatória")
    @Column(nullable = false)
    private String descricao;

    @NotNull(message = "O valor da receita é obrigatório")
    @DecimalMin(value = "0.01", message = "O valor da receita deve ser maior que zero")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @NotNull(message = "O tipo da receita é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_receita", nullable = false)
    private TipoReceita tipoReceita;

    @NotNull(message = "A forma de pagamento é obrigatória")
    @Enumerated(EnumType.STRING)
    @Column(name = "forma_pagamento", nullable = false)
    private FormaPagamento formaPagamento;

    @Column(name = "data_recebimento", nullable = false)
    private LocalDateTime dataRecebimento = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "ordem_servico_id")
    private OrdemServico ordemServico;

    @Column(nullable = false)
    private Boolean ativa = true;

    @Column(name = "observacao", columnDefinition = "TEXT")
    private String observacao;

    public Receita() {
    }

    public Receita(
            String descricao,
            BigDecimal valor,
            TipoReceita tipoReceita,
            FormaPagamento formaPagamento,
            Pedido pedido,
            OrdemServico ordemServico,
            String observacao
    ) {
        this.descricao = descricao;
        this.valor = valor;
        this.tipoReceita = tipoReceita;
        this.formaPagamento = formaPagamento;
        this.pedido = pedido;
        this.ordemServico = ordemServico;
        this.observacao = observacao;
        this.ativa = true;
        this.dataRecebimento = LocalDateTime.now();
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

    public TipoReceita getTipoReceita() {
        return tipoReceita;
    }

    public void setTipoReceita(TipoReceita tipoReceita) {
        this.tipoReceita = tipoReceita;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public LocalDateTime getDataRecebimento() {
        return dataRecebimento;
    }

    public void setDataRecebimento(LocalDateTime dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public OrdemServico getOrdemServico() {
        return ordemServico;
    }

    public void setOrdemServico(OrdemServico ordemServico) {
        this.ordemServico = ordemServico;
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
