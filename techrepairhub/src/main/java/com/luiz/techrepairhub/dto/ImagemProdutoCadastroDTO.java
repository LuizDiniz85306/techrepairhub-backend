package com.luiz.techrepairhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ImagemProdutoCadastroDTO {

    @NotNull(message = "O id do produto é obrigatório")
    private Long produtoId;

    @NotBlank(message = "A URL da imagem é obrigatória")
    private String urlImagem;

    @NotNull(message = "Informe se a imagem é principal")
    private Boolean imagemPrincipal;

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public Boolean getImagemPrincipal() {
        return imagemPrincipal;
    }

    public void setImagemPrincipal(Boolean imagemPrincipal) {
        this.imagemPrincipal = imagemPrincipal;
    }
}