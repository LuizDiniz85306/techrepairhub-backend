package com.luiz.techrepairhub.dto.relatorio;

public record OrdemPorTecnicoRelatorioDTO(
        Long tecnicoId,
        String tecnico,
        Long quantidadeOrdens
) {
}
