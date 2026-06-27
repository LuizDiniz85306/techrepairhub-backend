package com.luiz.techrepairhub.repository;

import com.luiz.techrepairhub.entity.Orcamento;
import com.luiz.techrepairhub.entity.StatusOrcamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrcamentoRepository extends JpaRepository<Orcamento, Long> {

    List<Orcamento> findByOrdemServicoIdOrderByDataCriacaoDesc(Long ordemServicoId);

    List<Orcamento> findByStatus(StatusOrcamento status);

    Optional<Orcamento> findByOrdemServicoIdAndStatus(Long ordemServicoId, StatusOrcamento status);

    boolean existsByOrdemServicoIdAndStatus(Long ordemServicoId, StatusOrcamento status);
}
