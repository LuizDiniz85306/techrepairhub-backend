package com.luiz.techrepairhub.dto;

import jakarta.validation.constraints.NotBlank;

public class RelatorioTecnicoAtualizarDTO {

    @NotBlank(message = "O problema relatado é obrigatório")
    private String problemaRelatado;

    @NotBlank(message = "O diagnóstico é obrigatório")
    private String diagnostico;

    @NotBlank(message = "Os procedimentos executados são obrigatórios")
    private String procedimentosExecutados;

    private String testesEfetuados;

    private String resultadoObtido;

    private String observacoesAdicionais;

    public String getProblemaRelatado() {
        return problemaRelatado;
    }

    public void setProblemaRelatado(String problemaRelatado) {
        this.problemaRelatado = problemaRelatado;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getProcedimentosExecutados() {
        return procedimentosExecutados;
    }

    public void setProcedimentosExecutados(String procedimentosExecutados) {
        this.procedimentosExecutados = procedimentosExecutados;
    }

    public String getTestesEfetuados() {
        return testesEfetuados;
    }

    public void setTestesEfetuados(String testesEfetuados) {
        this.testesEfetuados = testesEfetuados;
    }

    public String getResultadoObtido() {
        return resultadoObtido;
    }

    public void setResultadoObtido(String resultadoObtido) {
        this.resultadoObtido = resultadoObtido;
    }

    public String getObservacoesAdicionais() {
        return observacoesAdicionais;
    }

    public void setObservacoesAdicionais(String observacoesAdicionais) {
        this.observacoesAdicionais = observacoesAdicionais;
    }
}