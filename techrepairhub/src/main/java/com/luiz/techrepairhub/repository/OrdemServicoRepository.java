package com.luiz.techrepairhub.repository;

import com.luiz.techrepairhub.entity.OrdemServico;
import com.luiz.techrepairhub.entity.StatusOrdemServico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long> {

    List<OrdemServico> findByClienteId(Long clienteId);

    List<OrdemServico> findByEquipamentoId(Long equipamentoId);

    List<OrdemServico> findByTecnicoId(Long tecnicoId);

    List<OrdemServico> findByStatus(StatusOrdemServico status);

    long countByStatus(StatusOrdemServico status);
}