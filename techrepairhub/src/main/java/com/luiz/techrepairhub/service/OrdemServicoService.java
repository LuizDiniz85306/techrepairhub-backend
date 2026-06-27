package com.luiz.techrepairhub.service;

import com.luiz.techrepairhub.dto.OrdemServicoAtualizarDTO;
import com.luiz.techrepairhub.dto.OrdemServicoCadastroDTO;
import com.luiz.techrepairhub.dto.OrdemServicoResumoDTO;
import com.luiz.techrepairhub.entity.*;
import com.luiz.techrepairhub.repository.ClienteRepository;
import com.luiz.techrepairhub.repository.EquipamentoRepository;
import com.luiz.techrepairhub.repository.OrdemServicoRepository;
import com.luiz.techrepairhub.repository.TecnicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class OrdemServicoService {

    private final OrdemServicoRepository ordemServicoRepository;
    private final ClienteRepository clienteRepository;
    private final EquipamentoRepository equipamentoRepository;
    private final TecnicoRepository tecnicoRepository;
    private final HistoricoOrdemServicoService historicoService;

    public OrdemServicoService(
            OrdemServicoRepository ordemServicoRepository,
            ClienteRepository clienteRepository,
            EquipamentoRepository equipamentoRepository,
            TecnicoRepository tecnicoRepository,
            HistoricoOrdemServicoService historicoService
    ) {
        this.ordemServicoRepository = ordemServicoRepository;
        this.clienteRepository = clienteRepository;
        this.equipamentoRepository = equipamentoRepository;
        this.tecnicoRepository = tecnicoRepository;
        this.historicoService = historicoService;
    }

    @Transactional
    public OrdemServicoResumoDTO abrir(OrdemServicoCadastroDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com id: " + dto.getClienteId()));

        if (!cliente.getAtivo()) {
            throw new RuntimeException("Não é possível abrir OS para cliente inativo.");
        }

        Equipamento equipamento = equipamentoRepository.findById(dto.getEquipamentoId())
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado com id: " + dto.getEquipamentoId()));

        if (!equipamento.getAtivo()) {
            throw new RuntimeException("Não é possível abrir OS para equipamento inativo.");
        }

        if (!equipamento.getCliente().getId().equals(cliente.getId())) {
            throw new RuntimeException("Este equipamento não pertence ao cliente informado.");
        }

        Tecnico tecnico = null;

        if (dto.getTecnicoId() != null) {
            tecnico = tecnicoRepository.findById(dto.getTecnicoId())
                    .orElseThrow(() -> new RuntimeException("Técnico não encontrado com id: " + dto.getTecnicoId()));

            if (!tecnico.getAtivo()) {
                throw new RuntimeException("Não é possível atribuir OS a técnico inativo.");
            }
        }

        OrdemServico ordemServico = new OrdemServico(
                cliente,
                equipamento,
                tecnico,
                dto.getDescricaoProblema()
        );

        OrdemServico ordemSalva = ordemServicoRepository.save(ordemServico);

        historicoService.registrarAutomatico(
                ordemSalva,
                TipoHistoricoOrdemServico.ABERTURA,
                "Ordem de serviço aberta.",
                null,
                ordemSalva.getStatus()
        );

        if (tecnico != null) {
            historicoService.registrarAutomatico(
                    ordemSalva,
                    TipoHistoricoOrdemServico.ATRIBUICAO_TECNICO,
                    "Técnico atribuído: " + tecnico.getUsuario().getNome() + ".",
                    ordemSalva.getStatus(),
                    ordemSalva.getStatus()
            );
        }

        return montarResumo(ordemSalva);
    }

    public List<OrdemServicoResumoDTO> listarTodas() {
        return ordemServicoRepository.findAll()
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    public OrdemServico buscarEntidadePorId(Long id) {
        return ordemServicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ordem de serviço não encontrada com id: " + id));
    }

    public OrdemServicoResumoDTO buscarPorId(Long id) {
        return montarResumo(buscarEntidadePorId(id));
    }

    public List<OrdemServicoResumoDTO> listarPorCliente(Long clienteId) {
        return ordemServicoRepository.findByClienteId(clienteId)
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    public List<OrdemServicoResumoDTO> listarPorEquipamento(Long equipamentoId) {
        return ordemServicoRepository.findByEquipamentoId(equipamentoId)
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    public List<OrdemServicoResumoDTO> listarPorTecnico(Long tecnicoId) {
        return ordemServicoRepository.findByTecnicoId(tecnicoId)
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    public List<OrdemServicoResumoDTO> listarPorStatus(StatusOrdemServico status) {
        return ordemServicoRepository.findByStatus(status)
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    @Transactional
    public OrdemServicoResumoDTO atualizar(Long id, OrdemServicoAtualizarDTO dto) {
        OrdemServico ordemServico = buscarEntidadePorId(id);

        if (ordemServico.getStatus() == StatusOrdemServico.FINALIZADA) {
            throw new RuntimeException("Não é possível atualizar uma OS finalizada.");
        }

        if (ordemServico.getStatus() == StatusOrdemServico.CANCELADA) {
            throw new RuntimeException("Não é possível atualizar uma OS cancelada.");
        }

        StatusOrdemServico statusAnterior = ordemServico.getStatus();
        String diagnosticoAnterior = ordemServico.getDiagnostico();
        String solucaoAnterior = ordemServico.getSolucaoAplicada();

        Long tecnicoAnteriorId = null;
        if (ordemServico.getTecnico() != null) {
            tecnicoAnteriorId = ordemServico.getTecnico().getId();
        }

        Tecnico tecnico = null;

        if (dto.getTecnicoId() != null) {
            tecnico = tecnicoRepository.findById(dto.getTecnicoId())
                    .orElseThrow(() -> new RuntimeException("Técnico não encontrado com id: " + dto.getTecnicoId()));

            if (!tecnico.getAtivo()) {
                throw new RuntimeException("Não é possível atribuir OS a técnico inativo.");
            }
        }

        ordemServico.setTecnico(tecnico);
        ordemServico.setDescricaoProblema(dto.getDescricaoProblema());
        ordemServico.setDiagnostico(dto.getDiagnostico());
        ordemServico.setSolucaoAplicada(dto.getSolucaoAplicada());
        ordemServico.setValorTotal(dto.getValorTotal());

        if (dto.getStatus() == StatusOrdemServico.FINALIZADA) {
            ordemServico.setDataFinalizacao(LocalDateTime.now());
        }

        ordemServico.setStatus(dto.getStatus());

        OrdemServico ordemSalva = ordemServicoRepository.save(ordemServico);

        Long tecnicoNovoId = null;
        if (ordemSalva.getTecnico() != null) {
            tecnicoNovoId = ordemSalva.getTecnico().getId();
        }

        if (!Objects.equals(tecnicoAnteriorId, tecnicoNovoId) && ordemSalva.getTecnico() != null) {
            historicoService.registrarAutomatico(
                    ordemSalva,
                    TipoHistoricoOrdemServico.ATRIBUICAO_TECNICO,
                    "Técnico atribuído: " + ordemSalva.getTecnico().getUsuario().getNome() + ".",
                    statusAnterior,
                    ordemSalva.getStatus()
            );
        }

        if (!statusAnterior.equals(ordemSalva.getStatus())) {
            TipoHistoricoOrdemServico tipoHistorico = TipoHistoricoOrdemServico.ALTERACAO_STATUS;
            String descricao = "Status alterado de " + statusAnterior + " para " + ordemSalva.getStatus() + ".";

            if (ordemSalva.getStatus() == StatusOrdemServico.FINALIZADA) {
                tipoHistorico = TipoHistoricoOrdemServico.FINALIZACAO;
                descricao = "Ordem de serviço finalizada.";
            }

            if (ordemSalva.getStatus() == StatusOrdemServico.CANCELADA) {
                tipoHistorico = TipoHistoricoOrdemServico.CANCELAMENTO;
                descricao = "Ordem de serviço cancelada.";
            }

            historicoService.registrarAutomatico(
                    ordemSalva,
                    tipoHistorico,
                    descricao,
                    statusAnterior,
                    ordemSalva.getStatus()
            );
        }

        if (ordemSalva.getDiagnostico() != null
                && !ordemSalva.getDiagnostico().isBlank()
                && !Objects.equals(diagnosticoAnterior, ordemSalva.getDiagnostico())) {

            historicoService.registrarAutomatico(
                    ordemSalva,
                    TipoHistoricoOrdemServico.DIAGNOSTICO,
                    "Diagnóstico registrado: " + ordemSalva.getDiagnostico(),
                    statusAnterior,
                    ordemSalva.getStatus()
            );
        }

        if (ordemSalva.getSolucaoAplicada() != null
                && !ordemSalva.getSolucaoAplicada().isBlank()
                && !Objects.equals(solucaoAnterior, ordemSalva.getSolucaoAplicada())) {

            historicoService.registrarAutomatico(
                    ordemSalva,
                    TipoHistoricoOrdemServico.SOLUCAO,
                    "Solução aplicada: " + ordemSalva.getSolucaoAplicada(),
                    statusAnterior,
                    ordemSalva.getStatus()
            );
        }

        return montarResumo(ordemSalva);
    }

    @Transactional
    public OrdemServicoResumoDTO atribuirTecnico(Long ordemServicoId, Long tecnicoId) {
        OrdemServico ordemServico = buscarEntidadePorId(ordemServicoId);

        if (ordemServico.getStatus() == StatusOrdemServico.FINALIZADA ||
                ordemServico.getStatus() == StatusOrdemServico.CANCELADA) {
            throw new RuntimeException("Não é possível atribuir técnico a uma OS finalizada ou cancelada.");
        }

        Tecnico tecnico = tecnicoRepository.findById(tecnicoId)
                .orElseThrow(() -> new RuntimeException("Técnico não encontrado com id: " + tecnicoId));

        if (!tecnico.getAtivo()) {
            throw new RuntimeException("Não é possível atribuir OS a técnico inativo.");
        }

        StatusOrdemServico statusAnterior = ordemServico.getStatus();

        ordemServico.setTecnico(tecnico);

        OrdemServico ordemSalva = ordemServicoRepository.save(ordemServico);

        historicoService.registrarAutomatico(
                ordemSalva,
                TipoHistoricoOrdemServico.ATRIBUICAO_TECNICO,
                "Técnico atribuído: " + tecnico.getUsuario().getNome() + ".",
                statusAnterior,
                ordemSalva.getStatus()
        );

        return montarResumo(ordemSalva);
    }

    @Transactional
    public OrdemServicoResumoDTO finalizar(Long id) {
        OrdemServico ordemServico = buscarEntidadePorId(id);

        if (ordemServico.getStatus() == StatusOrdemServico.CANCELADA) {
            throw new RuntimeException("Não é possível finalizar OS cancelada.");
        }

        if (ordemServico.getStatus() == StatusOrdemServico.FINALIZADA) {
            throw new RuntimeException("OS já está finalizada.");
        }

        StatusOrdemServico statusAnterior = ordemServico.getStatus();

        ordemServico.setStatus(StatusOrdemServico.FINALIZADA);
        ordemServico.setDataFinalizacao(LocalDateTime.now());

        OrdemServico ordemSalva = ordemServicoRepository.save(ordemServico);

        historicoService.registrarAutomatico(
                ordemSalva,
                TipoHistoricoOrdemServico.FINALIZACAO,
                "Ordem de serviço finalizada.",
                statusAnterior,
                ordemSalva.getStatus()
        );

        return montarResumo(ordemSalva);
    }

    @Transactional
    public OrdemServicoResumoDTO cancelar(Long id) {
        OrdemServico ordemServico = buscarEntidadePorId(id);

        if (ordemServico.getStatus() == StatusOrdemServico.FINALIZADA) {
            throw new RuntimeException("Não é possível cancelar OS finalizada.");
        }

        if (ordemServico.getStatus() == StatusOrdemServico.CANCELADA) {
            throw new RuntimeException("OS já está cancelada.");
        }

        StatusOrdemServico statusAnterior = ordemServico.getStatus();

        ordemServico.setStatus(StatusOrdemServico.CANCELADA);

        OrdemServico ordemSalva = ordemServicoRepository.save(ordemServico);

        historicoService.registrarAutomatico(
                ordemSalva,
                TipoHistoricoOrdemServico.CANCELAMENTO,
                "Ordem de serviço cancelada.",
                statusAnterior,
                ordemSalva.getStatus()
        );

        return montarResumo(ordemSalva);
    }

    private OrdemServicoResumoDTO montarResumo(OrdemServico ordemServico) {
        String nomeTecnico = null;
        Long tecnicoId = null;

        if (ordemServico.getTecnico() != null) {
            tecnicoId = ordemServico.getTecnico().getId();
            nomeTecnico = ordemServico.getTecnico().getUsuario().getNome();
        }

        String equipamentoDescricao = ordemServico.getEquipamento().getTipo()
                + " "
                + ordemServico.getEquipamento().getMarca()
                + " "
                + ordemServico.getEquipamento().getModelo();

        return new OrdemServicoResumoDTO(
                ordemServico.getId(),
                ordemServico.getCliente().getId(),
                ordemServico.getCliente().getUsuario().getNome(),
                ordemServico.getEquipamento().getId(),
                equipamentoDescricao,
                tecnicoId,
                nomeTecnico,
                ordemServico.getDescricaoProblema(),
                ordemServico.getDiagnostico(),
                ordemServico.getSolucaoAplicada(),
                ordemServico.getStatus(),
                ordemServico.getValorTotal(),
                ordemServico.getDataAbertura(),
                ordemServico.getDataFinalizacao()
        );
    }
}