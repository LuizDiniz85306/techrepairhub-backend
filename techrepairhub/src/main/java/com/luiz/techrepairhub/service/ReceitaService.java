package com.luiz.techrepairhub.service;

import com.luiz.techrepairhub.dto.ReceitaAtualizarDTO;
import com.luiz.techrepairhub.dto.ReceitaCadastroDTO;
import com.luiz.techrepairhub.dto.ReceitaResumoDTO;
import com.luiz.techrepairhub.entity.OrdemServico;
import com.luiz.techrepairhub.entity.Pedido;
import com.luiz.techrepairhub.entity.Receita;
import com.luiz.techrepairhub.entity.TipoReceita;
import com.luiz.techrepairhub.repository.OrdemServicoRepository;
import com.luiz.techrepairhub.repository.PedidoRepository;
import com.luiz.techrepairhub.repository.ReceitaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class ReceitaService {

    private final ReceitaRepository receitaRepository;
    private final PedidoRepository pedidoRepository;
    private final OrdemServicoRepository ordemServicoRepository;

    public ReceitaService(
            ReceitaRepository receitaRepository,
            PedidoRepository pedidoRepository,
            OrdemServicoRepository ordemServicoRepository
    ) {
        this.receitaRepository = receitaRepository;
        this.pedidoRepository = pedidoRepository;
        this.ordemServicoRepository = ordemServicoRepository;
    }

    @Transactional
    public ReceitaResumoDTO cadastrar(ReceitaCadastroDTO dto) {
        validarVinculos(dto);

        Pedido pedido = null;
        OrdemServico ordemServico = null;

        if (dto.getPedidoId() != null) {
            pedido = pedidoRepository.findById(dto.getPedidoId())
                    .orElseThrow(() -> new RuntimeException("Pedido não encontrado com id: " + dto.getPedidoId()));
        }

        if (dto.getOrdemServicoId() != null) {
            ordemServico = ordemServicoRepository.findById(dto.getOrdemServicoId())
                    .orElseThrow(() -> new RuntimeException("Ordem de serviço não encontrada com id: " + dto.getOrdemServicoId()));
        }

        Receita receita = new Receita(
                dto.getDescricao(),
                dto.getValor(),
                dto.getTipoReceita(),
                dto.getFormaPagamento(),
                pedido,
                ordemServico,
                dto.getObservacao()
        );

        return montarResumo(receitaRepository.save(receita));
    }

    public List<ReceitaResumoDTO> listarTodas() {
        return receitaRepository.findAll()
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    public List<ReceitaResumoDTO> listarAtivas() {
        return receitaRepository.findByAtivaTrueOrderByDataRecebimentoDesc()
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    public ReceitaResumoDTO buscarPorId(Long id) {
        return montarResumo(buscarEntidadePorId(id));
    }

    public List<ReceitaResumoDTO> listarPorTipo(TipoReceita tipoReceita) {
        return receitaRepository.findByTipoReceitaOrderByDataRecebimentoDesc(tipoReceita)
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    public List<ReceitaResumoDTO> listarPorPedido(Long pedidoId) {
        return receitaRepository.findByPedidoId(pedidoId)
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    public List<ReceitaResumoDTO> listarPorOrdemServico(Long ordemServicoId) {
        return receitaRepository.findByOrdemServicoId(ordemServicoId)
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    public List<ReceitaResumoDTO> listarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return receitaRepository.findByDataRecebimentoBetweenOrderByDataRecebimentoDesc(inicio, fim)
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    @Transactional
    public ReceitaResumoDTO atualizar(Long id, ReceitaAtualizarDTO dto) {
        Receita receita = buscarEntidadePorId(id);

        receita.setDescricao(dto.getDescricao());
        receita.setValor(dto.getValor());
        receita.setTipoReceita(dto.getTipoReceita());
        receita.setFormaPagamento(dto.getFormaPagamento());
        receita.setObservacao(dto.getObservacao());

        return montarResumo(receitaRepository.save(receita));
    }

    @Transactional
    public ReceitaResumoDTO inativar(Long id) {
        Receita receita = buscarEntidadePorId(id);
        receita.setAtiva(false);
        return montarResumo(receitaRepository.save(receita));
    }

    @Transactional
    public ReceitaResumoDTO reativar(Long id) {
        Receita receita = buscarEntidadePorId(id);
        receita.setAtiva(true);
        return montarResumo(receitaRepository.save(receita));
    }

    private void validarVinculos(ReceitaCadastroDTO dto) {
        if (dto.getTipoReceita() == TipoReceita.VENDA && dto.getPedidoId() == null) {
            throw new RuntimeException("Receita do tipo VENDA deve estar vinculada a um pedido.");
        }

        if ((dto.getTipoReceita() == TipoReceita.SERVICO || dto.getTipoReceita() == TipoReceita.PECA)
                && dto.getOrdemServicoId() == null) {
            throw new RuntimeException("Receita do tipo SERVICO ou PECA deve estar vinculada a uma ordem de serviço.");
        }

        if (dto.getPedidoId() != null && dto.getOrdemServicoId() != null) {
            throw new RuntimeException("A receita não pode estar vinculada a pedido e ordem de serviço ao mesmo tempo.");
        }

        if (dto.getTipoReceita() == TipoReceita.OUTRA
                && (dto.getPedidoId() != null || dto.getOrdemServicoId() != null)) {
            throw new RuntimeException("Receita do tipo OUTRA não deve estar vinculada a pedido ou ordem de serviço.");
        }
    }

    private Receita buscarEntidadePorId(Long id) {
        return receitaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receita não encontrada com id: " + id));
    }

    private ReceitaResumoDTO montarResumo(Receita receita) {
        Long pedidoId = null;
        Long ordemServicoId = null;

        if (Objects.nonNull(receita.getPedido())) {
            pedidoId = receita.getPedido().getId();
        }

        if (Objects.nonNull(receita.getOrdemServico())) {
            ordemServicoId = receita.getOrdemServico().getId();
        }

        return new ReceitaResumoDTO(
                receita.getId(),
                receita.getDescricao(),
                receita.getValor(),
                receita.getTipoReceita(),
                receita.getFormaPagamento(),
                receita.getDataRecebimento(),
                pedidoId,
                ordemServicoId,
                receita.getAtiva(),
                receita.getObservacao()
        );
    }
}