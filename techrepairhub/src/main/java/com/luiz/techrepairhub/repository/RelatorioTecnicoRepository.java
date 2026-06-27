package com.luiz.techrepairhub.repository;

import com.luiz.techrepairhub.entity.RelatorioTecnico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RelatorioTecnicoRepository extends JpaRepository<RelatorioTecnico, Long> {

    Optional<RelatorioTecnico> findByOrdemServicoId(Long ordemServicoId);

    List<RelatorioTecnico> findByTecnicoIdOrderByDataRelatorioDesc(Long tecnicoId);

    boolean existsByOrdemServicoId(Long ordemServicoId);
}