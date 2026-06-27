package com.luiz.techrepairhub.service;

import com.luiz.techrepairhub.dto.FluxoCaixaResumoDTO;
import com.luiz.techrepairhub.entity.Despesa;
import com.luiz.techrepairhub.entity.Receita;
import com.luiz.techrepairhub.entity.ResultadoFluxoCaixa;
import com.luiz.techrepairhub.repository.DespesaRepository;
import com.luiz.techrepairhub.repository.ReceitaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FluxoCaixaService {

    private final ReceitaRepository receitaRepository;
    private final DespesaRepository despesaRepository;

    public FluxoCaixaService(
            ReceitaRepository receitaRepository,
            DespesaRepository despesaRepository
    ) {
        this.receitaRepository = receitaRepository;
        this.despesaRepository = despesaRepository;
    }

    public FluxoCaixaResumoDTO calcularFluxoCaixa(LocalDateTime inicio, LocalDateTime fim) {
        validarPeriodo(inicio, fim);

        List<Receita> receitas = receitaRepository
                .findByAtivaTrueAndDataRecebimentoBetweenOrderByDataRecebimentoDesc(inicio, fim);

        List<Despesa> despesas = despesaRepository
                .findByAtivaTrueAndPagaTrueAndDataDespesaBetweenOrderByDataDespesaDesc(inicio, fim);

        BigDecimal totalReceitas = receitas.stream()
                .map(Receita::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalDespesas = despesas.stream()
                .map(Despesa::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal saldoFinal = totalReceitas.subtract(totalDespesas);

        ResultadoFluxoCaixa resultado = definirResultado(saldoFinal);

        return new FluxoCaixaResumoDTO(
                inicio,
                fim,
                totalReceitas,
                totalDespesas,
                saldoFinal,
                receitas.size(),
                despesas.size(),
                resultado
        );
    }

    private void validarPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        if (inicio == null || fim == null) {
            throw new RuntimeException("O período inicial e final são obrigatórios.");
        }

        if (inicio.isAfter(fim)) {
            throw new RuntimeException("A data inicial não pode ser maior que a data final.");
        }
    }

    private ResultadoFluxoCaixa definirResultado(BigDecimal saldoFinal) {
        int comparacao = saldoFinal.compareTo(BigDecimal.ZERO);

        if (comparacao > 0) {
            return ResultadoFluxoCaixa.LUCRO;
        }

        if (comparacao < 0) {
            return ResultadoFluxoCaixa.PREJUIZO;
        }

        return ResultadoFluxoCaixa.EQUILIBRIO;
    }
}