package com.luiz.techrepairhub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "imagens_produto")
public class ImagemProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A URL da imagem é obrigatória")
    @Column(name = "url_imagem", nullable = false, columnDefinition = "TEXT")
    private String urlImagem;

    @Column(name = "imagem_principal", nullable = false)
    private Boolean imagemPrincipal = false;

    @Column(nullable = false)
    private Boolean ativo = true;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    public ImagemProduto() {
    }

    public ImagemProduto(String urlImagem, Boolean imagemPrincipal, Produto produto) {
        this.urlImagem = urlImagem;
        this.imagemPrincipal = imagemPrincipal;
        this.produto = produto;
        this.ativo = true;
    }

    public Long getId() {
        return id;
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

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}