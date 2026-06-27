package com.luiz.techrepairhub.dto;

import com.luiz.techrepairhub.entity.TipoHistoricoOrdemServico;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class HistoricoOrdemServicoCadastroDTO {

    @NotNull(message = "O id da ordem de serviço é obrigatório")
    private Long ordemServicoId;

    @NotNull(message = "O tipo do histórico é obrigatório")
    private TipoHistoricoOrdemServico tipo;

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    public Long getOrdemServicoId() {
        return ordemServicoId;
    }

    public void setOrdemServicoId(Long ordemServicoId) {
        this.ordemServicoId = ordemServicoId;
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
}