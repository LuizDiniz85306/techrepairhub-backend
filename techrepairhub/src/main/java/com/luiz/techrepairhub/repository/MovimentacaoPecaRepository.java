package com.luiz.techrepairhub.repository;

import com.luiz.techrepairhub.entity.MovimentacaoPeca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimentacaoPecaRepository extends JpaRepository<MovimentacaoPeca, Long> {

    List<MovimentacaoPeca> findByPecaIdOrderByDataMovimentacaoDesc(Long pecaId);
}
