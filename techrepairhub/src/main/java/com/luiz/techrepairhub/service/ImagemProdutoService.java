package com.luiz.techrepairhub.service;

import com.luiz.techrepairhub.dto.ImagemProdutoAtualizarDTO;
import com.luiz.techrepairhub.dto.ImagemProdutoCadastroDTO;
import com.luiz.techrepairhub.entity.ImagemProduto;
import com.luiz.techrepairhub.entity.Produto;
import com.luiz.techrepairhub.repository.ImagemProdutoRepository;
import com.luiz.techrepairhub.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagemProdutoService {

    private final ImagemProdutoRepository imagemProdutoRepository;
    private final ProdutoRepository produtoRepository;

    public ImagemProdutoService(
            ImagemProdutoRepository imagemProdutoRepository,
            ProdutoRepository produtoRepository
    ) {
        this.imagemProdutoRepository = imagemProdutoRepository;
        this.produtoRepository = produtoRepository;
    }

    public ImagemProduto cadastrar(ImagemProdutoCadastroDTO dto) {
        Produto produto = produtoRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com id: " + dto.getProdutoId()));

        if (!produto.getAtivo()) {
            throw new RuntimeException("Não é possível adicionar imagem a um produto inativo.");
        }

        if (dto.getImagemPrincipal()) {
            removerImagemPrincipalAtual(produto.getId());
        }

        ImagemProduto imagem = new ImagemProduto(
                dto.getUrlImagem(),
                dto.getImagemPrincipal(),
                produto
        );

        return imagemProdutoRepository.save(imagem);
    }

    public List<ImagemProduto> listarTodas() {
        return imagemProdutoRepository.findAll();
    }

    public List<ImagemProduto> listarPorProduto(Long produtoId) {
        return imagemProdutoRepository.findByProdutoId(produtoId);
    }

    public List<ImagemProduto> listarAtivasPorProduto(Long produtoId) {
        return imagemProdutoRepository.findByProdutoIdAndAtivoTrue(produtoId);
    }

    public ImagemProduto buscarPorId(Long id) {
        return imagemProdutoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Imagem não encontrada com id: " + id));
    }

    public ImagemProduto buscarImagemPrincipal(Long produtoId) {
        return imagemProdutoRepository.findByProdutoIdAndImagemPrincipalTrue(produtoId)
                .orElseThrow(() -> new RuntimeException("Imagem principal não encontrada para o produto id: " + produtoId));
    }

    public ImagemProduto atualizar(Long id, ImagemProdutoAtualizarDTO dto) {
        ImagemProduto imagem = buscarPorId(id);

        if (dto.getImagemPrincipal()) {
            removerImagemPrincipalAtual(imagem.getProduto().getId());
        }

        imagem.setUrlImagem(dto.getUrlImagem());
        imagem.setImagemPrincipal(dto.getImagemPrincipal());

        return imagemProdutoRepository.save(imagem);
    }

    public ImagemProduto definirComoPrincipal(Long id) {
        ImagemProduto imagem = buscarPorId(id);

        removerImagemPrincipalAtual(imagem.getProduto().getId());

        imagem.setImagemPrincipal(true);

        return imagemProdutoRepository.save(imagem);
    }

    public ImagemProduto inativar(Long id) {
        ImagemProduto imagem = buscarPorId(id);

        imagem.setAtivo(false);

        if (imagem.getImagemPrincipal()) {
            imagem.setImagemPrincipal(false);
        }

        return imagemProdutoRepository.save(imagem);
    }

    public ImagemProduto reativar(Long id) {
        ImagemProduto imagem = buscarPorId(id);
        imagem.setAtivo(true);
        return imagemProdutoRepository.save(imagem);
    }

    private void removerImagemPrincipalAtual(Long produtoId) {
        imagemProdutoRepository.findByProdutoIdAndImagemPrincipalTrue(produtoId)
                .ifPresent(imagemAtual -> {
                    imagemAtual.setImagemPrincipal(false);
                    imagemProdutoRepository.save(imagemAtual);
                });
    }
}
