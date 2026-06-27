package com.luiz.techrepairhub.service;

import com.luiz.techrepairhub.dto.PecaUtilizadaCadastroDTO;
import com.luiz.techrepairhub.dto.PecaUtilizadaResumoDTO;
import com.luiz.techrepairhub.entity.*;
import com.luiz.techrepairhub.repository.OrdemServicoRepository;
import com.luiz.techrepairhub.repository.PecaRepository;
import com.luiz.techrepairhub.repository.PecaUtilizadaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PecaUtilizadaService {

    private final PecaUtilizadaRepository pecaUtilizadaRepository;
    private final OrdemServicoRepository ordemServicoRepository;
    private final PecaRepository pecaRepository;
    private final HistoricoOrdemServicoService historicoService;

    public PecaUtilizadaService(
            PecaUtilizadaRepository pecaUtilizadaRepository,
            OrdemServicoRepository ordemServicoRepository,
            PecaRepository pecaRepository,
            HistoricoOrdemServicoService historicoService
    ) {
        this.pecaUtilizadaRepository = pecaUtilizadaRepository;
        this.ordemServicoRepository = ordemServicoRepository;
        this.pecaRepository = pecaRepository;
        this.historicoService = historicoService;
    }

    @Transactional
    public PecaUtilizadaResumoDTO adicionar(PecaUtilizadaCadastroDTO dto) {
        OrdemServico ordemServico = ordemServicoRepository.findById(dto.getOrdemServicoId())
                .orElseThrow(() -> new RuntimeException("Ordem de serviço não encontrada com id: " + dto.getOrdemServicoId()));

        if (ordemServico.getStatus() == StatusOrdemServico.FINALIZADA) {
            throw new RuntimeException("Não é possível adicionar peça em OS finalizada.");
        }

        if (ordemServico.getStatus() == StatusOrdemServico.CANCELADA) {
            throw new RuntimeException("Não é possível adicionar peça em OS cancelada.");
        }

        Peca peca = pecaRepository.findById(dto.getPecaId())
                .orElseThrow(() -> new RuntimeException("Peça não encontrada com id: " + dto.getPecaId()));

        if (!peca.getAtivo()) {
            throw new RuntimeException("Não é possível utilizar peça inativa.");
        }

        peca.removerEstoque(dto.getQuantidade());
        pecaRepository.save(peca);

        PecaUtilizada pecaUtilizada = new PecaUtilizada(
                ordemServico,
                peca,
                dto.getQuantidade(),
                peca.getPrecoVenda()
        );

        PecaUtilizada pecaUtilizadaSalva = pecaUtilizadaRepository.save(pecaUtilizada);

        ordemServico.setValorTotal(
                ordemServico.getValorTotal().add(pecaUtilizadaSalva.getSubtotal())
        );

        ordemServicoRepository.save(ordemServico);

        historicoService.registrarAutomatico(
                ordemServico,
                TipoHistoricoOrdemServico.OBSERVACAO,
                "Peça utilizada: " + peca.getNome()
                        + " | Quantidade: " + dto.getQuantidade()
                        + " | Subtotal: R$ " + pecaUtilizadaSalva.getSubtotal() + ".",
                ordemServico.getStatus(),
                ordemServico.getStatus()
        );

        return montarResumo(pecaUtilizadaSalva);
    }

    public List<PecaUtilizadaResumoDTO> listarTodas() {
        return pecaUtilizadaRepository.findAll()
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    public PecaUtilizadaResumoDTO buscarPorId(Long id) {
        PecaUtilizada pecaUtilizada = buscarEntidadePorId(id);
        return montarResumo(pecaUtilizada);
    }

    public List<PecaUtilizadaResumoDTO> listarPorOrdemServico(Long ordemServicoId) {
        return pecaUtilizadaRepository.findByOrdemServicoIdOrderByDataUtilizacaoDesc(ordemServicoId)
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    public List<PecaUtilizadaResumoDTO> listarPorPeca(Long pecaId) {
        return pecaUtilizadaRepository.findByPecaIdOrderByDataUtilizacaoDesc(pecaId)
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    @Transactional
    public void remover(Long id) {
        PecaUtilizada pecaUtilizada = buscarEntidadePorId(id);

        OrdemServico ordemServico = pecaUtilizada.getOrdemServico();

        if (ordemServico.getStatus() == StatusOrdemServico.FINALIZADA) {
            throw new RuntimeException("Não é possível remover peça de OS finalizada.");
        }

        if (ordemServico.getStatus() == StatusOrdemServico.CANCELADA) {
            throw new RuntimeException("Não é possível remover peça de OS cancelada.");
        }

        Peca peca = pecaUtilizada.getPeca();

        peca.adicionarEstoque(pecaUtilizada.getQuantidade());
        pecaRepository.save(peca);

        ordemServico.setValorTotal(
                ordemServico.getValorTotal().subtract(pecaUtilizada.getSubtotal())
        );

        ordemServicoRepository.save(ordemServico);

        historicoService.registrarAutomatico(
                ordemServico,
                TipoHistoricoOrdemServico.OBSERVACAO,
                "Peça removida da OS: " + peca.getNome()
                        + " | Quantidade devolvida ao estoque: " + pecaUtilizada.getQuantidade() + ".",
                ordemServico.getStatus(),
                ordemServico.getStatus()
        );

        pecaUtilizadaRepository.delete(pecaUtilizada);
    }

    private PecaUtilizada buscarEntidadePorId(Long id) {
        return pecaUtilizadaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Peça utilizada não encontrada com id: " + id));
    }

    private PecaUtilizadaResumoDTO montarResumo(PecaUtilizada pecaUtilizada) {
        return new PecaUtilizadaResumoDTO(
                pecaUtilizada.getId(),
                pecaUtilizada.getOrdemServico().getId(),
                pecaUtilizada.getPeca().getId(),
                pecaUtilizada.getPeca().getNome(),
                pecaUtilizada.getQuantidade(),
                pecaUtilizada.getValorUnitario(),
                pecaUtilizada.getSubtotal(),
                pecaUtilizada.getDataUtilizacao()
        );
    }
}
