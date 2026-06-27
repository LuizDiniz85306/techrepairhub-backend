package com.luiz.techrepairhub.dto;

import java.time.LocalDateTime;
import java.util.List;

public class RelatorioTecnicoResumoDTO {

    private Long id;
    private Long ordemServicoId;
    private Long tecnicoId;
    private String tecnico;
    private String cliente;
    private String equipamento;
    private String problemaRelatado;
    private String diagnostico;
    private String procedimentosExecutados;
    private String testesEfetuados;
    private String resultadoObtido;
    private String observacoesAdicionais;
    private LocalDateTime dataRelatorio;
    private List<PecaUtilizadaResumoDTO> pecasUtilizadas;

    public RelatorioTecnicoResumoDTO(
            Long id,
            Long ordemServicoId,
            Long tecnicoId,
            String tecnico,
            String cliente,
            String equipamento,
            String problemaRelatado,
            String diagnostico,
            String procedimentosExecutados,
            String testesEfetuados,
            String resultadoObtido,
            String observacoesAdicionais,
            LocalDateTime dataRelatorio,
            List<PecaUtilizadaResumoDTO> pecasUtilizadas
    ) {
        this.id = id;
        this.ordemServicoId = ordemServicoId;
        this.tecnicoId = tecnicoId;
        this.tecnico = tecnico;
        this.cliente = cliente;
        this.equipamento = equipamento;
        this.problemaRelatado = problemaRelatado;
        this.diagnostico = diagnostico;
        this.procedimentosExecutados = procedimentosExecutados;
        this.testesEfetuados = testesEfetuados;
        this.resultadoObtido = resultadoObtido;
        this.observacoesAdicionais = observacoesAdicionais;
        this.dataRelatorio = dataRelatorio;
        this.pecasUtilizadas = pecasUtilizadas;
    }

    public Long getId() {
        return id;
    }

    public Long getOrdemServicoId() {
        return ordemServicoId;
    }

    public Long getTecnicoId() {
        return tecnicoId;
    }

    public String getTecnico() {
        return tecnico;
    }

    public String getCliente() {
        return cliente;
    }

    public String getEquipamento() {
        return equipamento;
    }

    public String getProblemaRelatado() {
        return problemaRelatado;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public String getProcedimentosExecutados() {
        return procedimentosExecutados;
    }

    public String getTestesEfetuados() {
        return testesEfetuados;
    }

    public String getResultadoObtido() {
        return resultadoObtido;
    }

    public String getObservacoesAdicionais() {
        return observacoesAdicionais;
    }

    public LocalDateTime getDataRelatorio() {
        return dataRelatorio;
    }

    public List<PecaUtilizadaResumoDTO> getPecasUtilizadas() {
        return pecasUtilizadas;
    }
}