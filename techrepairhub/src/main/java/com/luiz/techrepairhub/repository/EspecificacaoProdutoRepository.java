package com.luiz.techrepairhub.repository;

import com.luiz.techrepairhub.entity.EspecificacaoProduto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EspecificacaoProdutoRepository extends JpaRepository<EspecificacaoProduto, Long> {

    List<EspecificacaoProduto> findByProdutoId(Long produtoId);

    List<EspecificacaoProduto> findByProdutoIdAndAtivoTrue(Long produtoId);

    List<EspecificacaoProduto> findByNomeContainingIgnoreCase(String nome);

    List<EspecificacaoProduto> findByProdutoIdAndNomeContainingIgnoreCase(Long produtoId, String nome);

    Optional<EspecificacaoProduto> findByProdutoIdAndNomeIgnoreCase(Long produtoId, String nome);

    boolean existsByProdutoIdAndNomeIgnoreCase(Long produtoId, String nome);
}