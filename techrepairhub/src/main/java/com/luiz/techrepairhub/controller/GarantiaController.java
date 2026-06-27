package com.luiz.techrepairhub.controller;

import com.luiz.techrepairhub.dto.GarantiaResumoDTO;
import com.luiz.techrepairhub.entity.StatusGarantia;
import com.luiz.techrepairhub.service.GarantiaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/garantias")
public class GarantiaController {

    private final GarantiaService garantiaService;

    public GarantiaController(GarantiaService garantiaService) {
        this.garantiaService = garantiaService;
    }

    @GetMapping
    public List<GarantiaResumoDTO> listarTodas() {
        return garantiaService.listarTodas();
    }

    @GetMapping("/{id}")
    public GarantiaResumoDTO buscarPorId(@PathVariable Long id) {
        return garantiaService.buscarPorId(id);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<GarantiaResumoDTO> listarPorCliente(@PathVariable Long clienteId) {
        return garantiaService.listarPorCliente(clienteId);
    }

    @GetMapping("/pedido/{pedidoId}")
    public List<GarantiaResumoDTO> listarPorPedido(@PathVariable Long pedidoId) {
        return garantiaService.listarPorPedido(pedidoId);
    }

    @GetMapping("/produto/{produtoId}")
    public List<GarantiaResumoDTO> listarPorProduto(@PathVariable Long produtoId) {
        return garantiaService.listarPorProduto(produtoId);
    }

    @GetMapping("/status")
    public List<GarantiaResumoDTO> listarPorStatus(@RequestParam StatusGarantia status) {
        return garantiaService.listarPorStatus(status);
    }

    @PutMapping("/{id}/cancelar")
    public GarantiaResumoDTO cancelar(@PathVariable Long id) {
        return garantiaService.cancelar(id);
    }

    @PutMapping("/atualizar-expiradas")
    public void atualizarGarantiasExpiradas() {
        garantiaService.atualizarGarantiasExpiradas();
    }
}