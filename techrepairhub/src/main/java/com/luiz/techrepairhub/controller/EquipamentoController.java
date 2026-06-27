package com.luiz.techrepairhub.controller;

import com.luiz.techrepairhub.dto.EquipamentoAtualizarDTO;
import com.luiz.techrepairhub.dto.EquipamentoCadastroDTO;
import com.luiz.techrepairhub.dto.EquipamentoResumoDTO;
import com.luiz.techrepairhub.service.EquipamentoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipamentos")
public class EquipamentoController {

    private final EquipamentoService equipamentoService;

    public EquipamentoController(EquipamentoService equipamentoService) {
        this.equipamentoService = equipamentoService;
    }

    @PostMapping
    public EquipamentoResumoDTO cadastrar(@RequestBody @Valid EquipamentoCadastroDTO dto) {
        return equipamentoService.cadastrar(dto);
    }

    @GetMapping
    public List<EquipamentoResumoDTO> listarTodos() {
        return equipamentoService.listarTodos();
    }

    @GetMapping("/ativos")
    public List<EquipamentoResumoDTO> listarAtivos() {
        return equipamentoService.listarAtivos();
    }

    @GetMapping("/{id}")
    public EquipamentoResumoDTO buscarPorId(@PathVariable Long id) {
        return equipamentoService.buscarPorId(id);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<EquipamentoResumoDTO> listarPorCliente(@PathVariable Long clienteId) {
        return equipamentoService.listarPorCliente(clienteId);
    }

    @GetMapping("/cliente/{clienteId}/ativos")
    public List<EquipamentoResumoDTO> listarAtivosPorCliente(@PathVariable Long clienteId) {
        return equipamentoService.listarAtivosPorCliente(clienteId);
    }

    @GetMapping("/buscar/tipo")
    public List<EquipamentoResumoDTO> buscarPorTipo(@RequestParam String tipo) {
        return equipamentoService.buscarPorTipo(tipo);
    }

    @GetMapping("/buscar/marca")
    public List<EquipamentoResumoDTO> buscarPorMarca(@RequestParam String marca) {
        return equipamentoService.buscarPorMarca(marca);
    }

    @GetMapping("/buscar/modelo")
    public List<EquipamentoResumoDTO> buscarPorModelo(@RequestParam String modelo) {
        return equipamentoService.buscarPorModelo(modelo);
    }

    @PutMapping("/{id}")
    public EquipamentoResumoDTO atualizar(
            @PathVariable Long id,
            @RequestBody @Valid EquipamentoAtualizarDTO dto
    ) {
        return equipamentoService.atualizar(id, dto);
    }

    @PutMapping("/{id}/inativar")
    public EquipamentoResumoDTO inativar(@PathVariable Long id) {
        return equipamentoService.inativar(id);
    }

    @PutMapping("/{id}/reativar")
    public EquipamentoResumoDTO reativar(@PathVariable Long id) {
        return equipamentoService.reativar(id);
    }
}