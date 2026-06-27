package com.luiz.techrepairhub.repository;

import com.luiz.techrepairhub.entity.EstadoConservacao;
import com.luiz.techrepairhub.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByAtivoTrue();

    List<Produto> findByNomeContainingIgnoreCase(String nome);

    List<Produto> findByCategoriaId(Long categoriaId);

    List<Produto> findByCategoriaIdAndAtivoTrue(Long categoriaId);

    List<Produto> findByEstadoConservacao(EstadoConservacao estadoConservacao);

    List<Produto> findByPrecoBetween(BigDecimal precoMinimo, BigDecimal precoMaximo);
}
