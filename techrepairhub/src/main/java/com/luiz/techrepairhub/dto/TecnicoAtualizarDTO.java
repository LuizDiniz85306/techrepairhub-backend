package com.luiz.techrepairhub.dto;

import jakarta.validation.constraints.NotBlank;

public class TecnicoAtualizarDTO {

    @NotBlank(message = "A especialidade é obrigatória")
    private String especialidade;

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
}