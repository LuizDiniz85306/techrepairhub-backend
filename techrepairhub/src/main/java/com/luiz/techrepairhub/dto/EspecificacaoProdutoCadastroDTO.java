package com.luiz.techrepairhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EspecificacaoProdutoCadastroDTO {

    @NotNull(message = "O id do produto é obrigatório")
    private Long produtoId;

    @NotBlank(message = "O nome da especificação é obrigatório")
    private String nome;

    @NotBlank(message = "O valor da especificação é obrigatório")
    private String valor;

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }


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