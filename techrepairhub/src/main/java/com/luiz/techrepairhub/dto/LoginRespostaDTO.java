package com.luiz.techrepairhub.dto;

import com.luiz.techrepairhub.entity.PerfilUsuario;

public class LoginRespostaDTO {

    private String token;
    private Long usuarioId;
    private String nome;
    private String email;
    private PerfilUsuario perfil;

    public LoginRespostaDTO(String token, Long usuarioId, String nome, String email, PerfilUsuario perfil) {
        this.token = token;
        this.usuarioId = usuarioId;
        this.nome = nome;
        this.email = email;
        this.perfil = perfil;
    }

    public String getToken() {
        return token;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public PerfilUsuario getPerfil() {
        return perfil;
    }
}