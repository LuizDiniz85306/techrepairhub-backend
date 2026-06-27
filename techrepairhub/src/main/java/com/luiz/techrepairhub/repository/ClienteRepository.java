package com.luiz.techrepairhub.repository;

import com.luiz.techrepairhub.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findByAtivoTrue();

    Optional<Cliente> findByCpf(String cpf);

    Optional<Cliente> findByUsuarioId(Long usuarioId);

    boolean existsByCpf(String cpf);

    boolean existsByUsuarioId(Long usuarioId);

    List<Cliente> findByUsuarioNomeContainingIgnoreCase(String nome);

    List<Cliente> findByUsuarioEmailContainingIgnoreCase(String email);

    List<Cliente> findByTelefoneContaining(String telefone);
}