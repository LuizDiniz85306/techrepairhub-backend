package com.luiz.techrepairhub.repository;

import com.luiz.techrepairhub.entity.MovimentacaoEstoque;
import com.luiz.techrepairhub.entity.TipoMovimentacaoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimentacaoEstoqueRepository extends JpaRepository<MovimentacaoEstoque, Long> {

    List<MovimentacaoEstoque> findByEstoqueId(Long estoqueId);

    List<MovimentacaoEstoque> findByEstoqueProdutoId(Long produtoId);

    List<MovimentacaoEstoque> findByTipo(TipoMovimentacaoEstoque tipo);
}