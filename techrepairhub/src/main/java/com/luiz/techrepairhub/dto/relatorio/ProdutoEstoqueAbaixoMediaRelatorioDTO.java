package com.luiz.techrepairhub.dto.relatorio;

public record ProdutoEstoqueAbaixoMediaRelatorioDTO(
        Long produtoId,
        String produto,
        Integer quantidadeAtual
) {
}
