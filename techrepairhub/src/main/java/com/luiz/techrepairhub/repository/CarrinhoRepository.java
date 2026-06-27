package com.luiz.techrepairhub.repository;

import com.luiz.techrepairhub.entity.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {

    Optional<Carrinho> findByClienteId(Long clienteId);

    boolean existsByClienteId(Long clienteId);
}