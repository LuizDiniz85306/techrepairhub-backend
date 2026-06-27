package com.luiz.techrepairhub.dto.relatorio;

import java.math.BigDecimal;

public record TotalVendidoClienteRelatorioDTO(
        Long clienteId,
        String cliente,
        Long quantidadePedidos,
        BigDecimal totalGasto
) {
}