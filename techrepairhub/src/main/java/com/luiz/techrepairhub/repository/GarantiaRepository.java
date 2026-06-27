package com.luiz.techrepairhub.repository;

import com.luiz.techrepairhub.entity.Garantia;
import com.luiz.techrepairhub.entity.StatusGarantia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GarantiaRepository extends JpaRepository<Garantia, Long> {

    List<Garantia> findByClienteId(Long clienteId);

    List<Garantia> findByPedidoId(Long pedidoId);

    List<Garantia> findByProdutoId(Long produtoId);

    List<Garantia> findByStatus(StatusGarantia status);

    Optional<Garantia> findByItemPedidoId(Long itemPedidoId);
}