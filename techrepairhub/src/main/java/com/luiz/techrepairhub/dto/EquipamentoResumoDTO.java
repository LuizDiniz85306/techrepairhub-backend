package com.luiz.techrepairhub.dto;

import java.time.LocalDateTime;

public class EquipamentoResumoDTO {

    private Long equipamentoId;
    private Long clienteId;
    private String nomeCliente;
    private String tipo;
    private String marca;
    private String modelo;
    private String numeroSerie;
    private String descricao;
    private Boolean ativo;
    private LocalDateTime dataCadastro;

    public EquipamentoResumoDTO(
            Long equipamentoId,
            Long clienteId,
            String nomeCliente,
            String tipo,
            String marca,
            String modelo,
            String numeroSerie,
            String descricao,
            Boolean ativo,
            LocalDateTime dataCadastro
    ) {
        this.equipamentoId = equipamentoId;
        this.clienteId = clienteId;
        this.nomeCliente = nomeCliente;
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
        this.numeroSerie = numeroSerie;
        this.descricao = descricao;
        this.ativo = ativo;
        this.dataCadastro = dataCadastro;
    }

    public Long getEquipamentoId() {
        return equipamentoId;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public String getTipo() {
        return tipo;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public String getDescricao() {
        return descricao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }
}
