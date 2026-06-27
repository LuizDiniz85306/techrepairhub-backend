package com.luiz.techrepairhub.repository;

import com.luiz.techrepairhub.entity.HistoricoOrdemServico;
import com.luiz.techrepairhub.entity.TipoHistoricoOrdemServico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoricoOrdemServicoRepository extends JpaRepository<HistoricoOrdemServico, Long> {

    List<HistoricoOrdemServico> findByOrdemServicoIdOrderByDataRegistroAsc(Long ordemServicoId);

    List<HistoricoOrdemServico> findByOrdemServicoIdOrderByDataRegistroDesc(Long ordemServicoId);

    List<HistoricoOrdemServico> findByTipo(TipoHistoricoOrdemServico tipo);
}