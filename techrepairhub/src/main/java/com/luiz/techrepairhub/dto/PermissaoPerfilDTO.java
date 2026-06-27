package com.luiz.techrepairhub.dto;

import java.util.List;

public class PermissaoPerfilDTO {

    private String perfil;
    private String descricao;
    private List<String> permissoes;
    private List<String> modulosPermitidos;
    private List<String> rotasPermitidas;

    public PermissaoPerfilDTO(
            String perfil,
            String descricao,
            List<String> permissoes,
            List<String> modulosPermitidos,
            List<String> rotasPermitidas
    ) {
        this.perfil = perfil;
        this.descricao = descricao;
        this.permissoes = permissoes;
        this.modulosPermitidos = modulosPermitidos;
        this.rotasPermitidas = rotasPermitidas;
    }

    public String getPerfil() {
        return perfil;
    }

    public String getDescricao() {
        return descricao;
    }

    public List<String> getPermissoes() {
        return permissoes;
    }

    public List<String> getModulosPermitidos() {
        return modulosPermitidos;
    }

    public List<String> getRotasPermitidas() {
        return rotasPermitidas;
    }
}