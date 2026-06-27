package com.luiz.techrepairhub.repository;

import com.luiz.techrepairhub.entity.Pedido;
import com.luiz.techrepairhub.entity.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByClienteId(Long clienteId);

    List<Pedido> findByStatus(StatusPedido status);
}