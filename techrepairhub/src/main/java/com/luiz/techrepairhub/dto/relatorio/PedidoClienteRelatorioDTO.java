package com.luiz.techrepairhub.dto.relatorio;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PedidoClienteRelatorioDTO(
        Long pedidoId,
        String cliente,
        String status,
        BigDecimal valorTotal,
        LocalDateTime dataPedido
) {
}
