package com.luiz.techrepairhub.dto;

import jakarta.validation.constraints.NotBlank;

public class ClienteAtualizarDTO {

    @NotBlank(message = "O telefone é obrigatório")
    private String telefone;

    @NotBlank(message = "O WhatsApp é obrigatório")
    private String whatsapp;

    @NotBlank(message = "O endereço é obrigatório")
    private String endereco;

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}