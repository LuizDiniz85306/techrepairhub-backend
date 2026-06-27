package com.luiz.techrepairhub.repository;

import com.luiz.techrepairhub.entity.Equipamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {

    List<Equipamento> findByClienteId(Long clienteId);

    List<Equipamento> findByClienteIdAndAtivoTrue(Long clienteId);

    List<Equipamento> findByAtivoTrue();

    List<Equipamento> findByTipoContainingIgnoreCase(String tipo);

    List<Equipamento> findByMarcaContainingIgnoreCase(String marca);

    List<Equipamento> findByModeloContainingIgnoreCase(String modelo);

    Optional<Equipamento> findByNumeroSerie(String numeroSerie);
}
