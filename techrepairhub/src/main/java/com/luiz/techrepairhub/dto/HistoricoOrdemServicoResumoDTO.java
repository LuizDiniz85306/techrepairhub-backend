package com.luiz.techrepairhub.dto;

import com.luiz.techrepairhub.entity.StatusOrdemServico;
import com.luiz.techrepairhub.entity.TipoHistoricoOrdemServico;

import java.time.LocalDateTime;

public class HistoricoOrdemServicoResumoDTO {

    private Long historicoId;
    private Long ordemServicoId;
    private TipoHistoricoOrdemServico tipo;
    private String descricao;
    private StatusOrdemServico statusAnterior;
    private StatusOrdemServico statusNovo;
    private LocalDateTime dataRegistro;

    public HistoricoOrdemServicoResumoDTO(
            Long historicoId,
            Long ordemServicoId,
            TipoHistoricoOrdemServico tipo,
            String descricao,
            StatusOrdemServico statusAnterior,
            StatusOrdemServico statusNovo,
            LocalDateTime dataRegistro
    ) {
        this.historicoId = historicoId;
        this.ordemServicoId = ordemServicoId;
        this.tipo = tipo;
        this.descricao = descricao;
        this.statusAnterior = statusAnterior;
        this.statusNovo = statusNovo;
        this.dataRegistro = dataRegistro;
    }

    public Long getHistoricoId() {
        return historicoId;
    }

    public Long getOrdemServicoId() {
        return ordemServicoId;
    }

    public TipoHistoricoOrdemServico getTipo() {
        return tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public StatusOrdemServico getStatusAnterior() {
        return statusAnterior;
    }

    public StatusOrdemServico getStatusNovo() {
        return statusNovo;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }
}