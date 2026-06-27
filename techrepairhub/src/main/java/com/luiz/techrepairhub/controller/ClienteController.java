package com.luiz.techrepairhub.controller;

import com.luiz.techrepairhub.dto.ClienteAtualizarDTO;
import com.luiz.techrepairhub.dto.ClienteCadastroDTO;
import com.luiz.techrepairhub.entity.Cliente;
import com.luiz.techrepairhub.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public Cliente cadastrar(@RequestBody @Valid ClienteCadastroDTO dto) {
        return clienteService.cadastrar(dto);
    }

    @GetMapping
    public List<Cliente> listarTodos() {
        return clienteService.listarTodos();
    }

    @GetMapping("/ativos")
    public List<Cliente> listarAtivos() {
        return clienteService.listarAtivos();
    }

    @GetMapping("/{id}")
    public Cliente buscarPorId(@PathVariable Long id) {
        return clienteService.buscarPorId(id);
    }

    @GetMapping("/usuario/{usuarioId}")
    public Cliente buscarPorUsuarioId(@PathVariable Long usuarioId) {
        return clienteService.buscarPorUsuarioId(usuarioId);
    }

    @GetMapping("/cpf/{cpf}")
    public Cliente buscarPorCpf(@PathVariable String cpf) {
        return clienteService.buscarPorCpf(cpf);
    }

    @GetMapping("/buscar/nome")
    public List<Cliente> buscarPorNome(@RequestParam String nome) {
        return clienteService.buscarPorNome(nome);
    }

    @GetMapping("/buscar/email")
    public List<Cliente> buscarPorEmail(@RequestParam String email) {
        return clienteService.buscarPorEmail(email);
    }

    @GetMapping("/buscar/telefone")
    public List<Cliente> buscarPorTelefone(@RequestParam String telefone) {
        return clienteService.buscarPorTelefone(telefone);
    }

    @PutMapping("/{id}")
    public Cliente atualizar(
            @PathVariable Long id,
            @RequestBody @Valid ClienteAtualizarDTO dto
    ) {
        return clienteService.atualizar(id, dto);
    }

    @PutMapping("/{id}/inativar")
    public Cliente inativar(@PathVariable Long id) {
        return clienteService.inativar(id);
    }
}