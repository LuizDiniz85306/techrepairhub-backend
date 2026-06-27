package com.luiz.techrepairhub.service;

import com.luiz.techrepairhub.dto.DashboardResumoDTO;
import com.luiz.techrepairhub.entity.*;
import com.luiz.techrepairhub.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DashboardService {

    private final ClienteRepository clienteRepository;
    private final TecnicoRepository tecnicoRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoRepository pedidoRepository;
    private final OrdemServicoRepository ordemServicoRepository;
    private final PecaRepository pecaRepository;
    private final ReceitaRepository receitaRepository;
    private final DespesaRepository despesaRepository;

    public DashboardService(
            ClienteRepository clienteRepository,
            TecnicoRepository tecnicoRepository,
            ProdutoRepository produtoRepository,
            PedidoRepository pedidoRepository,
            OrdemServicoRepository ordemServicoRepository,
            PecaRepository pecaRepository,
            ReceitaRepository receitaRepository,
            DespesaRepository despesaRepository
    ) {
        this.clienteRepository = clienteRepository;
        this.tecnicoRepository = tecnicoRepository;
        this.produtoRepository = produtoRepository;
        this.pedidoRepository = pedidoRepository;
        this.ordemServicoRepository = ordemServicoRepository;
        this.pecaRepository = pecaRepository;
        this.receitaRepository = receitaRepository;
        this.despesaRepository = despesaRepository;
    }

    public DashboardResumoDTO gerarDashboard(LocalDateTime inicio, LocalDateTime fim) {
        validarPeriodo(inicio, fim);

        Long totalClientes = clienteRepository.count();
        Long totalTecnicos = tecnicoRepository.count();
        Long totalProdutos = produtoRepository.count();
        Long totalPedidos = pedidoRepository.count();

        Long totalOrdensServico = ordemServicoRepository.count();

        Long osAbertas = ordemServicoRepository.countByStatus(StatusOrdemServico.ABERTA);
        Long osEmAnalise = ordemServicoRepository.countByStatus(StatusOrdemServico.EM_ANALISE);
        Long osAguardandoOrcamento = ordemServicoRepository.countByStatus(StatusOrdemServico.AGUARDANDO_ORCAMENTO);
        Long osAguardandoAprovacao = ordemServicoRepository.countByStatus(StatusOrdemServico.AGUARDANDO_APROVACAO);
        Long osEmExecucao = ordemServicoRepository.countByStatus(StatusOrdemServico.EM_EXECUCAO);
        Long osFinalizadas = ordemServicoRepository.countByStatus(StatusOrdemServico.FINALIZADA);
        Long osCanceladas = ordemServicoRepository.countByStatus(StatusOrdemServico.CANCELADA);

        Long pecasComEstoqueBaixo = pecaRepository.findAll()
                .stream()
                .filter(Peca::estoqueBaixo)
                .count();

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

        BigDecimal saldoFinanceiro = totalReceitas.subtract(totalDespesas);

        ResultadoFluxoCaixa resultadoFinanceiro = definirResultado(saldoFinanceiro);

        return new DashboardResumoDTO(
                inicio,
                fim,
                totalClientes,
                totalTecnicos,
                totalProdutos,
                totalPedidos,
                totalOrdensServico,
                osAbertas,
                osEmAnalise,
                osAguardandoOrcamento,
                osAguardandoAprovacao,
                osEmExecucao,
                osFinalizadas,
                osCanceladas,
                pecasComEstoqueBaixo,
                totalReceitas,
                totalDespesas,
                saldoFinanceiro,
                resultadoFinanceiro
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

    private ResultadoFluxoCaixa definirResultado(BigDecimal saldoFinanceiro) {
        int comparacao = saldoFinanceiro.compareTo(BigDecimal.ZERO);

        if (comparacao > 0) {
            return ResultadoFluxoCaixa.LUCRO;
        }

        if (comparacao < 0) {
            return ResultadoFluxoCaixa.PREJUIZO;
        }

        return ResultadoFluxoCaixa.EQUILIBRIO;
    }
}