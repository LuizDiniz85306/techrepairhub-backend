package com.luiz.techrepairhub.service;

import com.luiz.techrepairhub.dto.HistoricoOrdemServicoCadastroDTO;
import com.luiz.techrepairhub.dto.HistoricoOrdemServicoResumoDTO;
import com.luiz.techrepairhub.entity.HistoricoOrdemServico;
import com.luiz.techrepairhub.entity.OrdemServico;
import com.luiz.techrepairhub.entity.StatusOrdemServico;
import com.luiz.techrepairhub.entity.TipoHistoricoOrdemServico;
import com.luiz.techrepairhub.repository.HistoricoOrdemServicoRepository;
import com.luiz.techrepairhub.repository.OrdemServicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoricoOrdemServicoService {

    private final HistoricoOrdemServicoRepository historicoRepository;
    private final OrdemServicoRepository ordemServicoRepository;

    public HistoricoOrdemServicoService(
            HistoricoOrdemServicoRepository historicoRepository,
            OrdemServicoRepository ordemServicoRepository
    ) {
        this.historicoRepository = historicoRepository;
        this.ordemServicoRepository = ordemServicoRepository;
    }

    public HistoricoOrdemServicoResumoDTO registrarManual(HistoricoOrdemServicoCadastroDTO dto) {
        OrdemServico ordemServico = ordemServicoRepository.findById(dto.getOrdemServicoId())
                .orElseThrow(() -> new RuntimeException("Ordem de serviço não encontrada com id: " + dto.getOrdemServicoId()));

        HistoricoOrdemServico historico = new HistoricoOrdemServico(
                ordemServico,
                dto.getTipo(),
                dto.getDescricao(),
                null,
                ordemServico.getStatus()
        );

        return montarResumo(historicoRepository.save(historico));
    }

    public void registrarAutomatico(
            OrdemServico ordemServico,
            TipoHistoricoOrdemServico tipo,
            String descricao,
            StatusOrdemServico statusAnterior,
            StatusOrdemServico statusNovo
    ) {
        HistoricoOrdemServico historico = new HistoricoOrdemServico(
                ordemServico,
                tipo,
                descricao,
                statusAnterior,
                statusNovo
        );

        historicoRepository.save(historico);
    }

    public List<HistoricoOrdemServicoResumoDTO> listarTodos() {
        return historicoRepository.findAll()
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    public HistoricoOrdemServicoResumoDTO buscarPorId(Long id) {
        HistoricoOrdemServico historico = historicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Histórico não encontrado com id: " + id));

        return montarResumo(historico);
    }

    public List<HistoricoOrdemServicoResumoDTO> listarPorOrdemServico(Long ordemServicoId) {
        return historicoRepository.findByOrdemServicoIdOrderByDataRegistroAsc(ordemServicoId)
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    public List<HistoricoOrdemServicoResumoDTO> listarPorOrdemServicoDesc(Long ordemServicoId) {
        return historicoRepository.findByOrdemServicoIdOrderByDataRegistroDesc(ordemServicoId)
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    public List<HistoricoOrdemServicoResumoDTO> listarPorTipo(TipoHistoricoOrdemServico tipo) {
        return historicoRepository.findByTipo(tipo)
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    private HistoricoOrdemServicoResumoDTO montarResumo(HistoricoOrdemServico historico) {
        return new HistoricoOrdemServicoResumoDTO(
                historico.getId(),
                historico.getOrdemServico().getId(),
                historico.getTipo(),
                historico.getDescricao(),
                historico.getStatusAnterior(),
                historico.getStatusNovo(),
                historico.getDataRegistro()
        );
    }
}