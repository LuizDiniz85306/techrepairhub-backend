package com.luiz.techrepairhub.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class OrcamentoCadastroDTO {

    @NotNull(message = "O id da ordem de serviço é obrigatório")
    private Long ordemServicoId;

    @NotBlank(message = "A descrição do orçamento é obrigatória")
    private String descricao;

    @NotNull(message = "O valor da mão de obra é obrigatório")
    @DecimalMin(value = "0.00", message = "O valor da mão de obra não pode ser negativo")
    private BigDecimal valorMaoObra;

    @NotNull(message = "O valor das peças é obrigatório")
    @DecimalMin(value = "0.00", message = "O valor das peças não pode ser negativo")
    private BigDecimal valorPecas;

    public Long getOrdemServicoId() {
        return ordemServicoId;
    }

    public void setOrdemServicoId(Long ordemServicoId) {
        this.ordemServicoId = ordemServicoId;
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
}