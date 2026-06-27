package com.luiz.techrepairhub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pecas_utilizadas")
public class PecaUtilizada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A ordem de serviço é obrigatória")
    @ManyToOne
    @JoinColumn(name = "ordem_servico_id", nullable = false)
    private OrdemServico ordemServico;

    @NotNull(message = "A peça é obrigatória")
    @ManyToOne
    @JoinColumn(name = "peca_id", nullable = false)
    private Peca peca;

    @NotNull(message = "A quantidade é obrigatória")
    @Min(value = 1, message = "A quantidade deve ser maior que zero")
    @Column(nullable = false)
    private Integer quantidade;

    @NotNull(message = "O valor unitário é obrigatório")
    @Column(name = "valor_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorUnitario = BigDecimal.ZERO;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal = BigDecimal.ZERO;

    @Column(name = "data_utilizacao", nullable = false)
    private LocalDateTime dataUtilizacao = LocalDateTime.now();

    public PecaUtilizada() {
    }

    public PecaUtilizada(
            OrdemServico ordemServico,
            Peca peca,
            Integer quantidade,
            BigDecimal valorUnitario
    ) {
        this.ordemServico = ordemServico;
        this.peca = peca;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.dataUtilizacao = LocalDateTime.now();
        calcularSubtotal();
    }

    @PrePersist
    @PreUpdate
    public void calcularSubtotal() {
        BigDecimal valor = valorUnitario != null ? valorUnitario : BigDecimal.ZERO;
        Integer qtd = quantidade != null ? quantidade : 0;
        this.subtotal = valor.multiply(BigDecimal.valueOf(qtd));
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

    public Peca getPeca() {
        return peca;
    }

    public void setPeca(Peca peca) {
        this.peca = peca;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public LocalDateTime getDataUtilizacao() {
        return dataUtilizacao;
    }
}
