package com.luiz.techrepairhub.service;

import com.luiz.techrepairhub.dto.DespesaAtualizarDTO;
import com.luiz.techrepairhub.dto.DespesaCadastroDTO;
import com.luiz.techrepairhub.dto.DespesaResumoDTO;
import com.luiz.techrepairhub.entity.Despesa;
import com.luiz.techrepairhub.entity.TipoDespesa;
import com.luiz.techrepairhub.repository.DespesaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DespesaService {

    private final DespesaRepository despesaRepository;

    public DespesaService(DespesaRepository despesaRepository) {
        this.despesaRepository = despesaRepository;
    }

    @Transactional
    public DespesaResumoDTO cadastrar(DespesaCadastroDTO dto) {
        Despesa despesa = new Despesa(
                dto.getDescricao(),
                dto.getValor(),
                dto.getTipoDespesa(),
                dto.getFormaPagamento(),
                dto.getPaga(),
                dto.getObservacao()
        );

        return montarResumo(despesaRepository.save(despesa));
    }

    public List<DespesaResumoDTO> listarTodas() {
        return despesaRepository.findAll()
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    public List<DespesaResumoDTO> listarAtivas() {
        return despesaRepository.findByAtivaTrueOrderByDataDespesaDesc()
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    public DespesaResumoDTO buscarPorId(Long id) {
        return montarResumo(buscarEntidadePorId(id));
    }

    public List<DespesaResumoDTO> listarPorTipo(TipoDespesa tipoDespesa) {
        return despesaRepository.findByTipoDespesaOrderByDataDespesaDesc(tipoDespesa)
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    public List<DespesaResumoDTO> listarPorStatusPagamento(Boolean paga) {
        return despesaRepository.findByPagaOrderByDataDespesaDesc(paga)
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    public List<DespesaResumoDTO> listarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return despesaRepository.findByDataDespesaBetweenOrderByDataDespesaDesc(inicio, fim)
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    @Transactional
    public DespesaResumoDTO atualizar(Long id, DespesaAtualizarDTO dto) {
        Despesa despesa = buscarEntidadePorId(id);

        despesa.setDescricao(dto.getDescricao());
        despesa.setValor(dto.getValor());
        despesa.setTipoDespesa(dto.getTipoDespesa());
        despesa.setFormaPagamento(dto.getFormaPagamento());
        despesa.setPaga(dto.getPaga());
        despesa.setObservacao(dto.getObservacao());

        return montarResumo(despesaRepository.save(despesa));
    }

    @Transactional
    public DespesaResumoDTO marcarComoPaga(Long id) {
        Despesa despesa = buscarEntidadePorId(id);
        despesa.setPaga(true);
        return montarResumo(despesaRepository.save(despesa));
    }

    @Transactional
    public DespesaResumoDTO marcarComoPendente(Long id) {
        Despesa despesa = buscarEntidadePorId(id);
        despesa.setPaga(false);
        return montarResumo(despesaRepository.save(despesa));
    }

    @Transactional
    public DespesaResumoDTO inativar(Long id) {
        Despesa despesa = buscarEntidadePorId(id);
        despesa.setAtiva(false);
        return montarResumo(despesaRepository.save(despesa));
    }

    @Transactional
    public DespesaResumoDTO reativar(Long id) {
        Despesa despesa = buscarEntidadePorId(id);
        despesa.setAtiva(true);
        return montarResumo(despesaRepository.save(despesa));
    }

    private Despesa buscarEntidadePorId(Long id) {
        return despesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada com id: " + id));
    }

    private DespesaResumoDTO montarResumo(Despesa despesa) {
        return new DespesaResumoDTO(
                despesa.getId(),
                despesa.getDescricao(),
                despesa.getValor(),
                despesa.getTipoDespesa(),
                despesa.getFormaPagamento(),
                despesa.getDataDespesa(),
                despesa.getPaga(),
                despesa.getAtiva(),
                despesa.getObservacao()
        );
    }
}