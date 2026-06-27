package com.luiz.techrepairhub.repository;

import com.luiz.techrepairhub.entity.Peca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PecaRepository extends JpaRepository<Peca, Long> {

    List<Peca> findByAtivoTrue();

    List<Peca> findByNomeContainingIgnoreCase(String nome);

    List<Peca> findByAtivoTrueAndNomeContainingIgnoreCase(String nome);
}