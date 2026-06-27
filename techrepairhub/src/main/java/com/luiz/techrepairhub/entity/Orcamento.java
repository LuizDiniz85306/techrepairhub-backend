package com.luiz.techrepairhub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orcamentos")
public class Orcamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A ordem de serviço é obrigatória")
    @ManyToOne
    @JoinColumn(name = "ordem_servico_id", nullable = false)
    private OrdemServico ordemServico;

    @NotBlank(message = "A descrição do orçamento é obrigatória")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @NotNull(message = "O valor da mão de obra é obrigatório")
    @DecimalMin(value = "0.00", message = "O valor da mão de obra não pode ser negativo")
    @Column(name = "valor_mao_obra", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorMaoObra = BigDecimal.ZERO;

    @NotNull(message = "O valor das peças é obrigatório")
    @DecimalMin(value = "0.00", message = "O valor das peças não pode ser negativo")
    @Column(name = "valor_pecas", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorPecas = BigDecimal.ZERO;

    @Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusOrcamento status = StatusOrcamento.PENDENTE;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @Column(name = "data_resposta")
    private LocalDateTime dataResposta;

    public Orcamento() {
    }

    public Orcamento(
            OrdemServico ordemServico,
            String descricao,
            BigDecimal valorMaoObra,
            BigDecimal valorPecas
    ) {
        this.ordemServico = ordemServico;
        this.descricao = descricao;
        this.valorMaoObra = valorMaoObra;
        this.valorPecas = valorPecas;
        this.status = StatusOrcamento.PENDENTE;
        this.dataCriacao = LocalDateTime.now();
        calcularValorTotal();
    }

    @PrePersist
    @PreUpdate
    public void calcularValorTotal() {
        BigDecimal maoObra = valorMaoObra != null ? valorMaoObra : BigDecimal.ZERO;
        BigDecimal pecas = valorPecas != null ? valorPecas : BigDecimal.ZERO;
        this.valorTotal = maoObra.add(pecas);
    }

    public Long getId() {
        return id;
    }

    public OrdemServico getOrdemServico() {
        return ordemServico;
    }

    public void setOrdemServico(OrdemServico ordemServico) {
        this.ordemServico = ordemServico;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValorMaoObra() {
        return valorMaoObra;
    }

    public void setValorMaoObra(BigDecimal valorMaoObra) {
        this.valorMaoObra = valorMaoObra;
    }

    public BigDecimal getValorPecas() {
        return valorPecas;
    }

    public void setValorPecas(BigDecimal valorPecas) {
        this.valorPecas = valorPecas;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public StatusOrcamento getStatus() {
        return status;
    }

    public void setStatus(StatusOrcamento status) {
        this.status = status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataResposta() {
        return dataResposta;
    }

    public void setDataResposta(LocalDateTime dataResposta) {
        this.dataResposta = dataResposta;
    }
}