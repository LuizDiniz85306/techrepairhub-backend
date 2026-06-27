package com.luiz.techrepairhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TecnicoCadastroDTO {

    @NotNull(message = "O id do usuário é obrigatório")
    private Long usuarioId;

    @NotBlank(message = "A especialidade é obrigatória")
    private String especialidade;

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
}