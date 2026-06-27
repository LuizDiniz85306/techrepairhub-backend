package com.luiz.techrepairhub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "movimentacoes_peca")
public class MovimentacaoPeca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A peça é obrigatória")
    @ManyToOne
    @JoinColumn(name = "peca_id", nullable = false)
    private Peca peca;

    @NotNull(message = "O tipo de movimentação é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimentacao", nullable = false)
    private TipoMovimentacaoPeca tipoMovimentacao;

    @NotNull(message = "A quantidade é obrigatória")
    @Min(value = 0, message = "A quantidade não pode ser negativa")
    @Column(nullable = false)
    private Integer quantidade;

    @Column(name = "quantidade_anterior", nullable = false)
    private Integer quantidadeAnterior;

    @Column(name = "quantidade_nova", nullable = false)
    private Integer quantidadeNova;

    @Column(name = "data_movimentacao", nullable = false)
    private LocalDateTime dataMovimentacao = LocalDateTime.now();

    @Column(columnDefinition = "TEXT")
    private String observacao;

    public MovimentacaoPeca() {
    }

    public MovimentacaoPeca(
            Peca peca,
            TipoMovimentacaoPeca tipoMovimentacao,
            Integer quantidade,
            Integer quantidadeAnterior,
            Integer quantidadeNova,
            String observacao
    ) {
        this.peca = peca;
        this.tipoMovimentacao = tipoMovimentacao;
        this.quantidade = quantidade;
        this.quantidadeAnterior = quantidadeAnterior;
        this.quantidadeNova = quantidadeNova;
        this.observacao = observacao;
        this.dataMovimentacao = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Peca getPeca() {
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