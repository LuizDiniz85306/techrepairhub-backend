package com.luiz.techrepairhub.repository;

import com.luiz.techrepairhub.entity.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TecnicoRepository extends JpaRepository<Tecnico, Long> {

    List<Tecnico> findByAtivoTrue();

    List<Tecnico> findByEspecialidadeContainingIgnoreCase(String especialidade);

    Optional<Tecnico> findByUsuarioId(Long usuarioId);

    boolean existsByUsuarioId(Long usuarioId);
}