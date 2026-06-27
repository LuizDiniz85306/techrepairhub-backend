package com.luiz.techrepairhub.dto;

import java.math.BigDecimal;
import java.util.List;

public class CarrinhoResumoDTO {

    private Long carrinhoId;
    private Long clienteId;
    private String nomeCliente;
    private List<ItemCarrinhoResumoDTO> itens;
    private BigDecimal total;

    public CarrinhoResumoDTO(
            Long carrinhoId,
            Long clienteId,
            String nomeCliente,
            List<ItemCarrinhoResumoDTO> itens,
            BigDecimal total
    ) {
        this.carrinhoId = carrinhoId;
        this.clienteId = clienteId;
        this.nomeCliente = nomeCliente;
        this.itens = itens;
        this.total = total;
    }

    public Long getCarrinhoId() {
        return carrinhoId;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public List<ItemCarrinhoResumoDTO> getItens() {
        return itens;
    }

    public BigDecimal getTotal() {
        return total;
    }
}