package com.luiz.techrepairhub.dto;

import com.luiz.techrepairhub.entity.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoResumoDTO {

    private Long pedidoId;
    private Long clienteId;
    private String nomeCliente;
    private StatusPedido status;
    private BigDecimal valorTotal;
    private LocalDateTime dataPedido;
    private List<ItemPedidoResumoDTO> itens;

    public PedidoResumoDTO(
            Long pedidoId,
            Long clienteId,
            String nomeCliente,
            StatusPedido status,
            BigDecimal valorTotal,
            LocalDateTime dataPedido,
            List<ItemPedidoResumoDTO> itens
    ) {
        this.pedidoId = pedidoId;
        this.clienteId = clienteId;
        this.nomeCliente = nomeCliente;
        this.status = status;
        this.valorTotal = valorTotal;
        this.dataPedido = dataPedido;
        this.itens = itens;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public List<ItemPedidoResumoDTO> getItens() {
        return itens;
    }
}