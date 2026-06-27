package com.luiz.techrepairhub.dto;

import com.luiz.techrepairhub.entity.StatusGarantia;

import java.time.LocalDateTime;

public class GarantiaResumoDTO {

    private Long garantiaId;
    private Long pedidoId;
    private Long itemPedidoId;
    private Long clienteId;
    private String nomeCliente;
    private Long produtoId;
    private String nomeProduto;
    private Integer mesesGarantia;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private StatusGarantia status;

    public GarantiaResumoDTO(
            Long garantiaId,
            Long pedidoId,
            Long itemPedidoId,
            Long clienteId,
            String nomeCliente,
            Long produtoId,
            String nomeProduto,
            Integer mesesGarantia,
            LocalDateTime dataInicio,
            LocalDateTime dataFim,
            StatusGarantia status
    ) {
        this.garantiaId = garantiaId;
        this.pedidoId = pedidoId;
        this.itemPedidoId = itemPedidoId;
        this.clienteId = clienteId;
        this.nomeCliente = nomeCliente;
        this.produtoId = produtoId;
        this.nomeProduto = nomeProduto;
        this.mesesGarantia = mesesGarantia;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.status = status;
    }

    public Long getGarantiaId() {
        return garantiaId;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public Long getItemPedidoId() {
        return itemPedidoId;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public Integer getMesesGarantia() {
        return mesesGarantia;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public StatusGarantia getStatus() {
        return status;
    }
}