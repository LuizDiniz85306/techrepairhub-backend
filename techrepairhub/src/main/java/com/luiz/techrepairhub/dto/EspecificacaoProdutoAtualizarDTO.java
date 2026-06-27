package com.luiz.techrepairhub.dto;

import jakarta.validation.constraints.NotBlank;

public class EspecificacaoProdutoAtualizarDTO {

    @NotBlank(message = "O nome da especificação é obrigatório")
    private String nome;

    @NotBlank(message = "O valor da especificação é obrigatório")
    private String valor;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
