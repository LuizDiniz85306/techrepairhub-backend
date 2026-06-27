package com.luiz.techrepairhub.dto;

import com.luiz.techrepairhub.entity.FormaPagamento;
import com.luiz.techrepairhub.entity.TipoReceita;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReceitaResumoDTO {

    private Long id;
    private String descricao;
    private BigDecimal valor;
    private TipoReceita tipoReceita;
    private FormaPagamento formaPagamento;
    private LocalDateTime dataRecebimento;
    private Long pedidoId;
    private Long ordemServicoId;
    private Boolean ativa;
    private String observacao;

    public ReceitaResumoDTO(
            Long id,
            String descricao,
            BigDecimal valor,
            TipoReceita tipoReceita,
            FormaPagamento formaPagamento,
            LocalDateTime dataRecebimento,
            Long pedidoId,
            Long ordemServicoId,
            Boolean ativa,
            String observacao
    ) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.tipoReceita = tipoReceita;
        this.formaPagamento = formaPagamento;
        this.dataRecebimento = dataRecebimento;
        this.pedidoId = pedidoId;
        this.ordemServicoId = ordemServicoId;
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

    public TipoReceita getTipoReceita() {
        return tipoReceita;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public LocalDateTime getDataRecebimento() {
        return dataRecebimento;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public Long getOrdemServicoId() {
        return ordemServicoId;
    }

    public Boolean getAtiva() {
        return ativa;
    }

    public String getObservacao() {
        return observacao;
    }
}