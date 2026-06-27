package com.luiz.techrepairhub.service;

import com.luiz.techrepairhub.dto.*;
import com.luiz.techrepairhub.entity.*;
import com.luiz.techrepairhub.repository.OrdemServicoRepository;
import com.luiz.techrepairhub.repository.RelatorioTecnicoRepository;
import com.luiz.techrepairhub.repository.TecnicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RelatorioTecnicoService {

    private final RelatorioTecnicoRepository relatorioTecnicoRepository;
    private final OrdemServicoRepository ordemServicoRepository;
    private final TecnicoRepository tecnicoRepository;
    private final PecaUtilizadaService pecaUtilizadaService;
    private final HistoricoOrdemServicoService historicoService;

    public RelatorioTecnicoService(
            RelatorioTecnicoRepository relatorioTecnicoRepository,
            OrdemServicoRepository ordemServicoRepository,
            TecnicoRepository tecnicoRepository,
            PecaUtilizadaService pecaUtilizadaService,
            HistoricoOrdemServicoService historicoService
    ) {
        this.relatorioTecnicoRepository = relatorioTecnicoRepository;
        this.ordemServicoRepository = ordemServicoRepository;
        this.tecnicoRepository = tecnicoRepository;
        this.pecaUtilizadaService = pecaUtilizadaService;
        this.historicoService = historicoService;
    }

    @Transactional
    public RelatorioTecnicoResumoDTO criar(RelatorioTecnicoCadastroDTO dto) {
        OrdemServico ordemServico = ordemServicoRepository.findById(dto.getOrdemServicoId())
                .orElseThrow(() -> new RuntimeException("Ordem de serviço não encontrada com id: " + dto.getOrdemServicoId()));

        if (ordemServico.getStatus() == StatusOrdemServico.CANCELADA) {
            throw new RuntimeException("Não é possível criar relatório técnico para OS cancelada.");
        }

        if (relatorioTecnicoRepository.existsByOrdemServicoId(ordemServico.getId())) {
            throw new RuntimeException("Já existe relatório técnico para esta OS.");
        }

        Tecnico tecnico = tecnicoRepository.findById(dto.getTecnicoId())
                .orElseThrow(() -> new RuntimeException("Técnico não encontrado com id: " + dto.getTecnicoId()));

        if (!tecnico.getAtivo()) {
            throw new RuntimeException("Não é possível criar relatório com técnico inativo.");
        }

        if (ordemServico.getTecnico() != null
                && !ordemServico.getTecnico().getId().equals(tecnico.getId())) {
            throw new RuntimeException("O técnico informado não é o técnico atribuído à OS.");
        }

        RelatorioTecnico relatorio = new RelatorioTecnico(
                ordemServico,
                tecnico,
                dto.getProblemaRelatado(),
                dto.getDiagnostico(),
                dto.getProcedimentosExecutados(),
                dto.getTestesEfetuados(),
                dto.getResultadoObtido(),
                dto.getObservacoesAdicionais()
        );

        RelatorioTecnico relatorioSalvo = relatorioTecnicoRepository.save(relatorio);

        ordemServico.setDiagnostico(dto.getDiagnostico());
        ordemServico.setSolucaoAplicada(dto.getProcedimentosExecutados());
        ordemServicoRepository.save(ordemServico);

        historicoService.registrarAutomatico(
                ordemServico,
                TipoHistoricoOrdemServico.DIAGNOSTICO,
                "Relatório técnico criado. Diagnóstico: " + dto.getDiagnostico(),
                ordemServico.getStatus(),
                ordemServico.getStatus()
        );

        historicoService.registrarAutomatico(
                ordemServico,
                TipoHistoricoOrdemServico.SOLUCAO,
                "Procedimentos executados registrados no relatório técnico.",
                ordemServico.getStatus(),
                ordemServico.getStatus()
        );

        return montarResumo(relatorioSalvo);
    }

    public List<RelatorioTecnicoResumoDTO> listarTodos() {
        return relatorioTecnicoRepository.findAll()
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    public RelatorioTecnicoResumoDTO buscarPorId(Long id) {
        return montarResumo(buscarEntidadePorId(id));
    }

    public RelatorioTecnicoResumoDTO buscarPorOrdemServico(Long ordemServicoId) {
        RelatorioTecnico relatorio = relatorioTecnicoRepository.findByOrdemServicoId(ordemServicoId)
                .orElseThrow(() -> new RuntimeException("Relatório técnico não encontrado para a OS id: " + ordemServicoId));

        return montarResumo(relatorio);
    }

    public List<RelatorioTecnicoResumoDTO> listarPorTecnico(Long tecnicoId) {
        return relatorioTecnicoRepository.findByTecnicoIdOrderByDataRelatorioDesc(tecnicoId)
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    @Transactional
    public RelatorioTecnicoResumoDTO atualizar(Long id, RelatorioTecnicoAtualizarDTO dto) {
        RelatorioTecnico relatorio = buscarEntidadePorId(id);

        OrdemServico ordemServico = relatorio.getOrdemServico();

        if (ordemServico.getStatus() == StatusOrdemServico.CANCELADA) {
            throw new RuntimeException("Não é possível atualizar relatório técnico de OS cancelada.");
        }

        relatorio.setProblemaRelatado(dto.getProblemaRelatado());
        relatorio.setDiagnostico(dto.getDiagnostico());
        relatorio.setProcedimentosExecutados(dto.getProcedimentosExecutados());
        relatorio.setTestesEfetuados(dto.getTestesEfetuados());
        relatorio.setResultadoObtido(dto.getResultadoObtido());
        relatorio.setObservacoesAdicionais(dto.getObservacoesAdicionais());

        RelatorioTecnico relatorioSalvo = relatorioTecnicoRepository.save(relatorio);

        ordemServico.setDiagnostico(dto.getDiagnostico());
        ordemServico.setSolucaoAplicada(dto.getProcedimentosExecutados());
        ordemServicoRepository.save(ordemServico);

        historicoService.registrarAutomatico(
                ordemServico,
                TipoHistoricoOrdemServico.OBSERVACAO,
                "Relatório técnico atualizado.",
                ordemServico.getStatus(),
                ordemServico.getStatus()
        );

        return montarResumo(relatorioSalvo);
    }

    @Transactional
    public void remover(Long id) {
        RelatorioTecnico relatorio = buscarEntidadePorId(id);

        OrdemServico ordemServico = relatorio.getOrdemServico();

        if (ordemServico.getStatus() == StatusOrdemServico.FINALIZADA) {
            throw new RuntimeException("Não é possível remover relatório técnico de OS finalizada.");
        }

        relatorioTecnicoRepository.delete(relatorio);

        historicoService.registrarAutomatico(
                ordemServico,
                TipoHistoricoOrdemServico.OBSERVACAO,
                "Relatório técnico removido.",
                ordemServico.getStatus(),
                ordemServico.getStatus()
        );
    }

    private RelatorioTecnico buscarEntidadePorId(Long id) {
        return relatorioTecnicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Relatório técnico não encontrado com id: " + id));
    }

    private RelatorioTecnicoResumoDTO montarResumo(RelatorioTecnico relatorio) {
        OrdemServico ordemServico = relatorio.getOrdemServico();

        String equipamento = ordemServico.getEquipamento().getTipo()
                + " "
                + ordemServico.getEquipamento().getMarca()
                + " "
                + ordemServico.getEquipamento().getModelo();

        List<PecaUtilizadaResumoDTO> pecasUtilizadas =
                pecaUtilizadaService.listarPorOrdemServico(ordemServico.getId());

        return new RelatorioTecnicoResumoDTO(
                relatorio.getId(),
                ordemServico.getId(),
                relatorio.getTecnico().getId(),
                relatorio.getTecnico().getUsuario().getNome(),
                ordemServico.getCliente().getUsuario().getNome(),
                equipamento,
                relatorio.getProblemaRelatado(),
                relatorio.getDiagnostico(),
                relatorio.getProcedimentosExecutados(),
                relatorio.getTestesEfetuados(),
                relatorio.getResultadoObtido(),
                relatorio.getObservacoesAdicionais(),
                relatorio.getDataRelatorio(),
                pecasUtilizadas
        );
    }
}