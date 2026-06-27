package com.luiz.techrepairhub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "historicos_ordem_servico")
public class HistoricoOrdemServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A ordem de serviço é obrigatória")
    @ManyToOne
    @JoinColumn(name = "ordem_servico_id", nullable = false)
    private OrdemServico ordemServico;

    @NotNull(message = "O tipo do histórico é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoHistoricoOrdemServico tipo;

    @NotBlank(message = "A descrição do histórico é obrigatória")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "status_anterior")
    @Enumerated(EnumType.STRING)
    private StatusOrdemServico statusAnterior;

    @Column(name = "status_novo")
    @Enumerated(EnumType.STRING)
    private StatusOrdemServico statusNovo;

    @Column(name = "data_registro", nullable = false)
    private LocalDateTime dataRegistro = LocalDateTime.now();

    public HistoricoOrdemServico() {
    }

    public HistoricoOrdemServico(
            OrdemServico ordemServico,
            TipoHistoricoOrdemServico tipo,
            String descricao,
            StatusOrdemServico statusAnterior,
            StatusOrdemServico statusNovo
    ) {
        this.ordemServico = ordemServico;
        this.tipo = tipo;
        this.descricao = descricao;
        this.statusAnterior = statusAnterior;
        this.statusNovo = statusNovo;
        this.dataRegistro = LocalDateTime.now();
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

    public TipoHistoricoOrdemServico getTipo() {
        return tipo;
    }

    public void setTipo(TipoHistoricoOrdemServico tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusOrdemServico getStatusAnterior() {
        return statusAnterior;
    }

    public void setStatusAnterior(StatusOrdemServico statusAnterior) {
        this.statusAnterior = statusAnterior;
    }

    public StatusOrdemServico getStatusNovo() {
        return statusNovo;
    }

    public void setStatusNovo(StatusOrdemServico statusNovo) {
        this.statusNovo = statusNovo;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDateTime dataRegistro) {
        this.dataRegistro = dataRegistro;
    }
}