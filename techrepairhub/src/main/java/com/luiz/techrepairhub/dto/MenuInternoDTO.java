package com.luiz.techrepairhub.dto;

public class MenuInternoDTO {

    private String nome;
    private String rota;
    private String icone;
    private String descricao;

    public MenuInternoDTO(String nome, String rota, String icone, String descricao) {
        this.nome = nome;
        this.rota = rota;
        this.icone = icone;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public String getRota() {
        return rota;
    }

    public String getIcone() {
        return icone;
    }

    public String getDescricao() {
        return descricao;
    }
}