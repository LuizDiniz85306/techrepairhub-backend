package com.luiz.techrepairhub.repository;

import com.luiz.techrepairhub.entity.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

    Optional<Estoque> findByProdutoId(Long produtoId);

    boolean existsByProdutoId(Long produtoId);

    List<Estoque> findByAtivoTrue();

    List<Estoque> findByQuantidadeAtualLessThanEqual(Integer quantidade);

    List<Estoque> findByQuantidadeAtualGreaterThan(Integer quantidade);
}