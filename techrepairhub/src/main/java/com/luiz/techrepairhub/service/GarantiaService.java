package com.luiz.techrepairhub.service;

import com.luiz.techrepairhub.dto.GarantiaResumoDTO;
import com.luiz.techrepairhub.entity.Garantia;
import com.luiz.techrepairhub.entity.ItemPedido;
import com.luiz.techrepairhub.entity.Pedido;
import com.luiz.techrepairhub.entity.StatusGarantia;
import com.luiz.techrepairhub.repository.GarantiaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GarantiaService {

    private final GarantiaRepository garantiaRepository;

    public GarantiaService(GarantiaRepository garantiaRepository) {
        this.garantiaRepository = garantiaRepository;
    }

    @Transactional
    public Garantia gerarGarantia(Pedido pedido, ItemPedido itemPedido) {
        if (garantiaRepository.findByItemPedidoId(itemPedido.getId()).isPresent()) {
            throw new RuntimeException("Este item de pedido já possui garantia cadastrada.");
        }

        Garantia garantia = new Garantia(pedido, itemPedido);

        return garantiaRepository.save(garantia);
    }

    public List<GarantiaResumoDTO> listarTodas() {
        atualizarGarantiasExpiradas();

        return garantiaRepository.findAll()
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    public GarantiaResumoDTO buscarPorId(Long id) {
        Garantia garantia = garantiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Garantia não encontrada com id: " + id));

        atualizarStatusSeExpirada(garantia);

        return montarResumo(garantia);
    }

    public List<GarantiaResumoDTO> listarPorCliente(Long clienteId) {
        atualizarGarantiasExpiradas();

        return garantiaRepository.findByClienteId(clienteId)
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    public List<GarantiaResumoDTO> listarPorPedido(Long pedidoId) {
        atualizarGarantiasExpiradas();

        return garantiaRepository.findByPedidoId(pedidoId)
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    public List<GarantiaResumoDTO> listarPorProduto(Long produtoId) {
        atualizarGarantiasExpiradas();

        return garantiaRepository.findByProdutoId(produtoId)
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    public List<GarantiaResumoDTO> listarPorStatus(StatusGarantia status) {
        atualizarGarantiasExpiradas();

        return garantiaRepository.findByStatus(status)
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    @Transactional
    public GarantiaResumoDTO cancelar(Long id) {
        Garantia garantia = garantiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Garantia não encontrada com id: " + id));

        if (garantia.getStatus() == StatusGarantia.CANCELADA) {
            throw new RuntimeException("Garantia já está cancelada.");
        }

        garantia.setStatus(StatusGarantia.CANCELADA);
        garantiaRepository.save(garantia);

        return montarResumo(garantia);
    }

    @Transactional
    public void atualizarGarantiasExpiradas() {
        List<Garantia> garantias = garantiaRepository.findByStatus(StatusGarantia.ATIVA);

        for (Garantia garantia : garantias) {
            atualizarStatusSeExpirada(garantia);
        }
    }

    private void atualizarStatusSeExpirada(Garantia garantia) {
        if (garantia.getStatus() == StatusGarantia.ATIVA && garantia.estaExpirada()) {
            garantia.setStatus(StatusGarantia.EXPIRADA);
            garantiaRepository.save(garantia);
        }
    }

    private GarantiaResumoDTO montarResumo(Garantia garantia) {
        return new GarantiaResumoDTO(
                garantia.getId(),
                garantia.getPedido().getId(),
                garantia.getItemPedido().getId(),
                garantia.getCliente().getId(),
                garantia.getCliente().getUsuario().getNome(),
                garantia.getProduto().getId(),
                garantia.getProduto().getNome(),
                garantia.getMesesGarantia(),
                garantia.getDataInicio(),
                garantia.getDataFim(),
                garantia.getStatus()
        );
    }
}