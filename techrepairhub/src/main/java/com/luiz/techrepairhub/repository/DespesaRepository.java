package com.luiz.techrepairhub.repository;

import com.luiz.techrepairhub.entity.Despesa;
import com.luiz.techrepairhub.entity.TipoDespesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    List<Despesa> findByAtivaTrueOrderByDataDespesaDesc();

    List<Despesa> findByTipoDespesaOrderByDataDespesaDesc(TipoDespesa tipoDespesa);

    List<Despesa> findByPagaOrderByDataDespesaDesc(Boolean paga);

    List<Despesa> findByDataDespesaBetweenOrderByDataDespesaDesc(
            LocalDateTime inicio,
            LocalDateTime fim
    );

    List<Despesa> findByAtivaTrueAndPagaTrueAndDataDespesaBetweenOrderByDataDespesaDesc(
            LocalDateTime inicio,
            LocalDateTime fim
    );
}