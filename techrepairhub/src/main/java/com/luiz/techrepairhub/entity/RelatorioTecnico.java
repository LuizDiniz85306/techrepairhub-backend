package com.luiz.techrepairhub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "relatorios_tecnicos")
public class RelatorioTecnico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A ordem de serviço é obrigatória")
    @OneToOne
    @JoinColumn(name = "ordem_servico_id", nullable = false, unique = true)
    private OrdemServico ordemServico;

    @NotNull(message = "O técnico é obrigatório")
    @ManyToOne
    @JoinColumn(name = "tecnico_id", nullable = false)
    private Tecnico tecnico;

    @NotBlank(message = "O problema relatado é obrigatório")
    @Column(name = "problema_relatado", nullable = false, columnDefinition = "TEXT")
    private String problemaRelatado;

    @NotBlank(message = "O diagnóstico é obrigatório")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String diagnostico;

    @NotBlank(message = "Os procedimentos executados são obrigatórios")
    @Column(name = "procedimentos_executados", nullable = false, columnDefinition = "TEXT")
    private String procedimentosExecutados;

    @Column(name = "testes_efetuados", columnDefinition = "TEXT")
    private String testesEfetuados;

    @Column(name = "resultado_obtido", columnDefinition = "TEXT")
    private String resultadoObtido;

    @Column(name = "observacoes_adicionais", columnDefinition = "TEXT")
    private String observacoesAdicionais;

    @Column(name = "data_relatorio", nullable = false)
    private LocalDateTime dataRelatorio = LocalDateTime.now();

    public RelatorioTecnico() {
    }

    public RelatorioTecnico(
            OrdemServico ordemServico,
            Tecnico tecnico,
            String problemaRelatado,
            String diagnostico,
            String procedimentosExecutados,
            String testesEfetuados,
            String resultadoObtido,
            String observacoesAdicionais
    ) {
        this.ordemServico = ordemServico;
        this.tecnico = tecnico;
        this.problemaRelatado = problemaRelatado;
        this.diagnostico = diagnostico;
        this.procedimentosExecutados = procedimentosExecutados;
        this.testesEfetuados = testesEfetuados;
        this.resultadoObtido = resultadoObtido;
        this.observacoesAdicionais = observacoesAdicionais;
        this.dataRelatorio = LocalDateTime.now();
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

    public Tecnico getTecnico() {
        return tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }

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

    public LocalDateTime getDataRelatorio() {
        return dataRelatorio;
    }
}