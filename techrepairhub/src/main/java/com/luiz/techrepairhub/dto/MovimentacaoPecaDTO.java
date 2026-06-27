package com.luiz.techrepairhub.dto;

import com.luiz.techrepairhub.entity.TipoMovimentacaoPeca;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class MovimentacaoPecaDTO {

    @NotNull(message = "O tipo de movimentação é obrigatório")
    private TipoMovimentacaoPeca tipoMovimentacao;

    @NotNull(message = "A quantidade é obrigatória")
    @Min(value = 0, message = "A quantidade não pode ser negativa")
    private Integer quantidade;

    private String observacao;

    public TipoMovimentacaoPeca getTipoMovimentacao() {
        return tipoMovimentacao;
    }

    public void setTipoMovimentacao(TipoMovimentacaoPeca tipoMovimentacao) {
        this.tipoMovimentacao = tipoMovimentacao;
    }


    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }


    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
