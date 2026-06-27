package com.luiz.techrepairhub.dto.relatorio;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PedidoAcimaMediaRelatorioDTO(
        Long pedidoId,
        BigDecimal valorTotal,
        String status,
        LocalDateTime dataPedido
) {
}