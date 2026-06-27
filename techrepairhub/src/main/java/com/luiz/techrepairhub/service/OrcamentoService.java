package com.luiz.techrepairhub.service;

import com.luiz.techrepairhub.dto.OrcamentoCadastroDTO;
import com.luiz.techrepairhub.dto.OrcamentoResumoDTO;
import com.luiz.techrepairhub.entity.*;
import com.luiz.techrepairhub.repository.OrcamentoRepository;
import com.luiz.techrepairhub.repository.OrdemServicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrcamentoService {

    private final OrcamentoRepository orcamentoRepository;
    private final OrdemServicoRepository ordemServicoRepository;
    private final HistoricoOrdemServicoService historicoService;

    public OrcamentoService(
            OrcamentoRepository orcamentoRepository,
            OrdemServicoRepository ordemServicoRepository,
            HistoricoOrdemServicoService historicoService
    ) {
        this.orcamentoRepository = orcamentoRepository;
        this.ordemServicoRepository = ordemServicoRepository;
        this.historicoService = historicoService;
    }

    @Transactional
    public OrcamentoResumoDTO criar(OrcamentoCadastroDTO dto) {
        OrdemServico ordemServico = ordemServicoRepository.findById(dto.getOrdemServicoId())
                .orElseThrow(() -> new RuntimeException("Ordem de serviço não encontrada com id: " + dto.getOrdemServicoId()));

        if (ordemServico.getStatus() == StatusOrdemServico.FINALIZADA) {
            throw new RuntimeException("Não é possível criar orçamento para OS finalizada.");
        }

        if (ordemServico.getStatus() == StatusOrdemServico.CANCELADA) {
            throw new RuntimeException("Não é possível criar orçamento para OS cancelada.");
        }

        boolean existeOrcamentoPendente = orcamentoRepository.existsByOrdemServicoIdAndStatus(
                ordemServico.getId(),
                StatusOrcamento.PENDENTE
        );

        if (existeOrcamentoPendente) {
            throw new RuntimeException("Já existe um orçamento pendente para esta OS.");
        }

        StatusOrdemServico statusAnterior = ordemServico.getStatus();

        Orcamento orcamento = new Orcamento(
                ordemServico,
                dto.getDescricao(),
                dto.getValorMaoObra(),
                dto.getValorPecas()
        );

        Orcamento orcamentoSalvo = orcamentoRepository.save(orcamento);

        ordemServico.setStatus(StatusOrdemServico.AGUARDANDO_APROVACAO);
        ordemServicoRepository.save(ordemServico);

        historicoService.registrarAutomatico(
                ordemServico,
                TipoHistoricoOrdemServico.ALTERACAO_STATUS,
                "Orçamento criado. Status alterado de " + statusAnterior + " para " + ordemServico.getStatus() + ".",
                statusAnterior,
                ordemServico.getStatus()
        );

        historicoService.registrarAutomatico(
                ordemServico,
                TipoHistoricoOrdemServico.OBSERVACAO,
                "Orçamento criado no valor total de R$ " + orcamentoSalvo.getValorTotal() + ".",
                ordemServico.getStatus(),
                ordemServico.getStatus()
        );

        return montarResumo(orcamentoSalvo);
    }

    public List<OrcamentoResumoDTO> listarTodos() {
        return orcamentoRepository.findAll()
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    public OrcamentoResumoDTO buscarPorId(Long id) {
        Orcamento orcamento = buscarEntidadePorId(id);
        return montarResumo(orcamento);
    }

    public List<OrcamentoResumoDTO> listarPorOrdemServico(Long ordemServicoId) {
        return orcamentoRepository.findByOrdemServicoIdOrderByDataCriacaoDesc(ordemServicoId)
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    public List<OrcamentoResumoDTO> listarPorStatus(StatusOrcamento status) {
        return orcamentoRepository.findByStatus(status)
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    @Transactional
    public OrcamentoResumoDTO aprovar(Long id) {
        Orcamento orcamento = buscarEntidadePorId(id);

        if (orcamento.getStatus() != StatusOrcamento.PENDENTE) {
            throw new RuntimeException("Somente orçamentos pendentes podem ser aprovados.");
        }

        OrdemServico ordemServico = orcamento.getOrdemServico();
        StatusOrdemServico statusAnterior = ordemServico.getStatus();

        orcamento.setStatus(StatusOrcamento.APROVADO);
        orcamento.setDataResposta(LocalDateTime.now());

        ordemServico.setStatus(StatusOrdemServico.EM_EXECUCAO);
        ordemServico.setValorTotal(orcamento.getValorTotal());

        ordemServicoRepository.save(ordemServico);
        Orcamento orcamentoSalvo = orcamentoRepository.save(orcamento);

        historicoService.registrarAutomatico(
                ordemServico,
                TipoHistoricoOrdemServico.ALTERACAO_STATUS,
                "Orçamento aprovado. Status alterado de " + statusAnterior + " para " + ordemServico.getStatus() + ".",
                statusAnterior,
                ordemServico.getStatus()
        );

        historicoService.registrarAutomatico(
                ordemServico,
                TipoHistoricoOrdemServico.OBSERVACAO,
                "Orçamento aprovado no valor total de R$ " + orcamentoSalvo.getValorTotal() + ".",
                ordemServico.getStatus(),
                ordemServico.getStatus()
        );

        return montarResumo(orcamentoSalvo);
    }

    @Transactional
    public OrcamentoResumoDTO recusar(Long id) {
        Orcamento orcamento = buscarEntidadePorId(id);

        if (orcamento.getStatus() != StatusOrcamento.PENDENTE) {
            throw new RuntimeException("Somente orçamentos pendentes podem ser recusados.");
        }

        OrdemServico ordemServico = orcamento.getOrdemServico();
        StatusOrdemServico statusAnterior = ordemServico.getStatus();

        orcamento.setStatus(StatusOrcamento.RECUSADO);
        orcamento.setDataResposta(LocalDateTime.now());

        ordemServico.setStatus(StatusOrdemServico.AGUARDANDO_ORCAMENTO);

        ordemServicoRepository.save(ordemServico);
        Orcamento orcamentoSalvo = orcamentoRepository.save(orcamento);

        historicoService.registrarAutomatico(
                ordemServico,
                TipoHistoricoOrdemServico.ALTERACAO_STATUS,
                "Orçamento recusado. Status alterado de " + statusAnterior + " para " + ordemServico.getStatus() + ".",
                statusAnterior,
                ordemServico.getStatus()
        );

        historicoService.registrarAutomatico(
                ordemServico,
                TipoHistoricoOrdemServico.OBSERVACAO,
                "Orçamento recusado.",
                ordemServico.getStatus(),
                ordemServico.getStatus()
        );

        return montarResumo(orcamentoSalvo);
    }

    private Orcamento buscarEntidadePorId(Long id) {
        return orcamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orçamento não encontrado com id: " + id));
    }

    private OrcamentoResumoDTO montarResumo(Orcamento orcamento) {
        OrdemServico ordemServico = orcamento.getOrdemServico();

        String equipamento = ordemServico.getEquipamento().getTipo()
                + " "
                + ordemServico.getEquipamento().getMarca()
                + " "
                + ordemServico.getEquipamento().getModelo();

        return new OrcamentoResumoDTO(
                orcamento.getId(),
                ordemServico.getId(),
                ordemServico.getCliente().getUsuario().getNome(),
                equipamento,
                orcamento.getDescricao(),
                orcamento.getValorMaoObra(),
                orcamento.getValorPecas(),
                orcamento.getValorTotal(),
                orcamento.getStatus(),
                orcamento.getDataCriacao(),
                orcamento.getDataResposta()
        );
    }
}