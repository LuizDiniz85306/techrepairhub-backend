package com.luiz.techrepairhub.dto.relatorio;

import java.math.BigDecimal;

public record ProdutoCategoriaEstoqueRelatorioDTO(
        Long produtoId,
        String produto,
        String categoria,
        BigDecimal preco,
        Integer quantidadeAtual,
        Integer estoqueMinimo
) {
}