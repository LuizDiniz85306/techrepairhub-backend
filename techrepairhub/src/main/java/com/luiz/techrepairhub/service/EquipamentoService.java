package com.luiz.techrepairhub.service;

import com.luiz.techrepairhub.dto.EquipamentoAtualizarDTO;
import com.luiz.techrepairhub.dto.EquipamentoCadastroDTO;
import com.luiz.techrepairhub.dto.EquipamentoResumoDTO;
import com.luiz.techrepairhub.entity.Cliente;
import com.luiz.techrepairhub.entity.Equipamento;
import com.luiz.techrepairhub.repository.ClienteRepository;
import com.luiz.techrepairhub.repository.EquipamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipamentoService {

    private final EquipamentoRepository equipamentoRepository;
    private final ClienteRepository clienteRepository;

    public EquipamentoService(
            EquipamentoRepository equipamentoRepository,
            ClienteRepository clienteRepository
    ) {
        this.equipamentoRepository = equipamentoRepository;
        this.clienteRepository = clienteRepository;
    }

    public EquipamentoResumoDTO cadastrar(EquipamentoCadastroDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com id: " + dto.getClienteId()));

        if (!cliente.getAtivo()) {
            throw new RuntimeException("Não é possível cadastrar equipamento para cliente inativo.");
        }

        if (dto.getNumeroSerie() != null && !dto.getNumeroSerie().isBlank()) {
            equipamentoRepository.findByNumeroSerie(dto.getNumeroSerie())
                    .ifPresent(equipamento -> {
                        throw new RuntimeException("Já existe equipamento cadastrado com este número de série.");
                    });
        }

        Equipamento equipamento = new Equipamento(
                dto.getTipo(),
                dto.getMarca(),
                dto.getModelo(),
                dto.getNumeroSerie(),
                dto.getDescricao(),
                cliente
        );

        return montarResumo(equipamentoRepository.save(equipamento));
    }

    public List<EquipamentoResumoDTO> listarTodos() {
        return equipamentoRepository.findAll()
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    public List<EquipamentoResumoDTO> listarAtivos() {
        return equipamentoRepository.findByAtivoTrue()
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    public Equipamento buscarEntidadePorId(Long id) {
        return equipamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado com id: " + id));
    }

    public EquipamentoResumoDTO buscarPorId(Long id) {
        return montarResumo(buscarEntidadePorId(id));
    }

    public List<EquipamentoResumoDTO> listarPorCliente(Long clienteId) {
        return equipamentoRepository.findByClienteId(clienteId)
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    public List<EquipamentoResumoDTO> listarAtivosPorCliente(Long clienteId) {
        return equipamentoRepository.findByClienteIdAndAtivoTrue(clienteId)
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    public List<EquipamentoResumoDTO> buscarPorTipo(String tipo) {
        return equipamentoRepository.findByTipoContainingIgnoreCase(tipo)
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    public List<EquipamentoResumoDTO> buscarPorMarca(String marca) {
        return equipamentoRepository.findByMarcaContainingIgnoreCase(marca)
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    public List<EquipamentoResumoDTO> buscarPorModelo(String modelo) {
        return equipamentoRepository.findByModeloContainingIgnoreCase(modelo)
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    public EquipamentoResumoDTO atualizar(Long id, EquipamentoAtualizarDTO dto) {
        Equipamento equipamento = buscarEntidadePorId(id);

        if (dto.getNumeroSerie() != null && !dto.getNumeroSerie().isBlank()) {
            equipamentoRepository.findByNumeroSerie(dto.getNumeroSerie())
                    .ifPresent(equipamentoExistente -> {
                        if (!equipamentoExistente.getId().equals(id)) {
                            throw new RuntimeException("Já existe outro equipamento com este número de série.");
                        }
                    });
        }

        equipamento.setTipo(dto.getTipo());
        equipamento.setMarca(dto.getMarca());
        equipamento.setModelo(dto.getModelo());
        equipamento.setNumeroSerie(dto.getNumeroSerie());
        equipamento.setDescricao(dto.getDescricao());

        return montarResumo(equipamentoRepository.save(equipamento));
    }

    public EquipamentoResumoDTO inativar(Long id) {
        Equipamento equipamento = buscarEntidadePorId(id);
        equipamento.setAtivo(false);
        return montarResumo(equipamentoRepository.save(equipamento));
    }

    public EquipamentoResumoDTO reativar(Long id) {
        Equipamento equipamento = buscarEntidadePorId(id);
        equipamento.setAtivo(true);
        return montarResumo(equipamentoRepository.save(equipamento));
    }

    private EquipamentoResumoDTO montarResumo(Equipamento equipamento) {
        return new EquipamentoResumoDTO(
                equipamento.getId(),
                equipamento.getCliente().getId(),
                equipamento.getCliente().getUsuario().getNome(),
                equipamento.getTipo(),
                equipamento.getMarca(),
                equipamento.getModelo(),
                equipamento.getNumeroSerie(),
                equipamento.getDescricao(),
                equipamento.getAtivo(),
                equipamento.getDataCadastro()
        );
    }
}