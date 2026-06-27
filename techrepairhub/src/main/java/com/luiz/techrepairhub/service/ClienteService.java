package com.luiz.techrepairhub.service;

import com.luiz.techrepairhub.dto.ClienteAtualizarDTO;
import com.luiz.techrepairhub.dto.ClienteCadastroDTO;
import com.luiz.techrepairhub.entity.Cliente;
import com.luiz.techrepairhub.entity.PerfilUsuario;
import com.luiz.techrepairhub.entity.Usuario;
import com.luiz.techrepairhub.repository.ClienteRepository;
import com.luiz.techrepairhub.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;

    public ClienteService(
            ClienteRepository clienteRepository,
            UsuarioRepository usuarioRepository
    ) {
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Cliente cadastrar(ClienteCadastroDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + dto.getUsuarioId()));

        if (!usuario.getAtivo()) {
            throw new RuntimeException("Não é possível cadastrar cliente para usuário inativo.");
        }

        if (usuario.getPerfil() != PerfilUsuario.CLIENTE) {
            throw new RuntimeException("O usuário informado não possui perfil de CLIENTE.");
        }

        if (clienteRepository.existsByUsuarioId(usuario.getId())) {
            throw new RuntimeException("Este usuário já está vinculado a um cliente.");
        }

        if (clienteRepository.existsByCpf(dto.getCpf())) {
            throw new RuntimeException("Já existe um cliente cadastrado com este CPF.");
        }

        Cliente cliente = new Cliente(
                usuario,
                dto.getCpf(),
                dto.getTelefone(),
                dto.getWhatsapp(),
                dto.getEndereco()
        );

        return clienteRepository.save(cliente);
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public List<Cliente> listarAtivos() {
        return clienteRepository.findByAtivoTrue();
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com id: " + id));
    }

    public Cliente buscarPorUsuarioId(Long usuarioId) {
        return clienteRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado para o usuário id: " + usuarioId));
    }

    public Cliente buscarPorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com CPF: " + cpf));
    }

    public List<Cliente> buscarPorNome(String nome) {
        return clienteRepository.findByUsuarioNomeContainingIgnoreCase(nome);
    }

    public List<Cliente> buscarPorEmail(String email) {
        return clienteRepository.findByUsuarioEmailContainingIgnoreCase(email);
    }

    public List<Cliente> buscarPorTelefone(String telefone) {
        return clienteRepository.findByTelefoneContaining(telefone);
    }

    public Cliente atualizar(Long id, ClienteAtualizarDTO dto) {
        Cliente cliente = buscarPorId(id);

        cliente.setTelefone(dto.getTelefone());
        cliente.setWhatsapp(dto.getWhatsapp());
        cliente.setEndereco(dto.getEndereco());

        return clienteRepository.save(cliente);
    }

    public Cliente inativar(Long id) {
        Cliente cliente = buscarPorId(id);
        cliente.setAtivo(false);
        return clienteRepository.save(cliente);
    }
}