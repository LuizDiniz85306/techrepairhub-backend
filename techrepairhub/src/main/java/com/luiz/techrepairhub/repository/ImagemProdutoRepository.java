package com.luiz.techrepairhub.repository;

import com.luiz.techrepairhub.entity.ImagemProduto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImagemProdutoRepository extends JpaRepository<ImagemProduto, Long> {

    List<ImagemProduto> findByProdutoId(Long produtoId);

    List<ImagemProduto> findByProdutoIdAndAtivoTrue(Long produtoId);

    Optional<ImagemProduto> findByProdutoIdAndImagemPrincipalTrue(Long produtoId);
}