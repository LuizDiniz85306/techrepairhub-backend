package com.luiz.techrepairhub.repository;

import com.luiz.techrepairhub.entity.Receita;
import com.luiz.techrepairhub.entity.TipoReceita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    List<Receita> findByAtivaTrueOrderByDataRecebimentoDesc();

    List<Receita> findByTipoReceitaOrderByDataRecebimentoDesc(TipoReceita tipoReceita);

    List<Receita> findByPedidoId(Long pedidoId);

    List<Receita> findByOrdemServicoId(Long ordemServicoId);

    List<Receita> findByDataRecebimentoBetweenOrderByDataRecebimentoDesc(
            LocalDateTime inicio,
            LocalDateTime fim
    );

    List<Receita> findByAtivaTrueAndDataRecebimentoBetweenOrderByDataRecebimentoDesc(
            LocalDateTime inicio,
            LocalDateTime fim
    );
}
