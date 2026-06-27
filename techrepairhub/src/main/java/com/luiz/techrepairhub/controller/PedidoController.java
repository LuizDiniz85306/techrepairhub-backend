package com.luiz.techrepairhub.controller;

import com.luiz.techrepairhub.dto.PedidoResumoDTO;
import com.luiz.techrepairhub.entity.StatusPedido;
import com.luiz.techrepairhub.service.PedidoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("/cliente/{clienteId}/finalizar")
    public PedidoResumoDTO finalizarPedido(@PathVariable Long clienteId) {
        return pedidoService.finalizarPedido(clienteId);
    }

    @GetMapping
    public List<PedidoResumoDTO> listarTodos() {
        return pedidoService.listarTodos();
    }

    @GetMapping("/{pedidoId}")
    public PedidoResumoDTO buscarPorId(@PathVariable Long pedidoId) {
        return pedidoService.buscarPorId(pedidoId);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<PedidoResumoDTO> listarPorCliente(@PathVariable Long clienteId) {
        return pedidoService.listarPorCliente(clienteId);
    }

    @GetMapping("/status")
    public List<PedidoResumoDTO> listarPorStatus(@RequestParam StatusPedido status) {
        return pedidoService.listarPorStatus(status);
    }

    @PutMapping("/{pedidoId}/cancelar")
    public PedidoResumoDTO cancelar(@PathVariable Long pedidoId) {
        return pedidoService.cancelar(pedidoId);
    }

    @PutMapping("/{pedidoId}/entregar")
    public PedidoResumoDTO marcarComoEntregue(@PathVariable Long pedidoId) {
        return pedidoService.marcarComoEntregue(pedidoId);
    }
}