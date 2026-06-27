package com.luiz.techrepairhub.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class PecaUtilizadaCadastroDTO {

    @NotNull(message = "O id da ordem de serviço é obrigatório")
    private Long ordemServicoId;

    @NotNull(message = "O id da peça é obrigatório")
    private Long pecaId;

    @NotNull(message = "A quantidade é obrigatória")
    @Min(value = 1, message = "A quantidade deve ser maior que zero")
    private Integer quantidade;

    public Long getOrdemServicoId() {
        return ordemServicoId;
    }

    public void setOrdemServicoId(Long ordemServicoId) {
        this.ordemServicoId = ordemServicoId;
    }

    public Long getPecaId() {
        return pecaId;
    }

    public void setPecaId(Long pecaId) {
        this.pecaId = pecaId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}