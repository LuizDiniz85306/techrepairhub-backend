package com.luiz.techrepairhub.repository;

import com.luiz.techrepairhub.entity.PecaUtilizada;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PecaUtilizadaRepository extends JpaRepository<PecaUtilizada, Long> {

    List<PecaUtilizada> findByOrdemServicoIdOrderByDataUtilizacaoDesc(Long ordemServicoId);

    List<PecaUtilizada> findByPecaIdOrderByDataUtilizacaoDesc(Long pecaId);
}