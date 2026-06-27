package com.luiz.techrepairhub.dto.relatorio;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrdemServicoRelatorioDTO(
        Long ordemServicoId,
        String cliente,
        String tipoEquipamento,
        String marca,
        String modelo,
        String tecnico,
        String status,
        BigDecimal valorTotal,
        LocalDateTime dataAbertura
) {
}