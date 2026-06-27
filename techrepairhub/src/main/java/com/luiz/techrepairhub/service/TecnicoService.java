package com.luiz.techrepairhub.service;

import com.luiz.techrepairhub.dto.TecnicoCadastroDTO;
import com.luiz.techrepairhub.entity.PerfilUsuario;
import com.luiz.techrepairhub.entity.Tecnico;
import com.luiz.techrepairhub.entity.Usuario;
import com.luiz.techrepairhub.repository.TecnicoRepository;
import com.luiz.techrepairhub.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TecnicoService {

    private final TecnicoRepository tecnicoRepository;
    private final UsuarioRepository usuarioRepository;

    public TecnicoService(
            TecnicoRepository tecnicoRepository,
            UsuarioRepository usuarioRepository
    ) {
        this.tecnicoRepository = tecnicoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Tecnico cadastrar(TecnicoCadastroDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + dto.getUsuarioId()));

        if (!usuario.getAtivo()) {
            throw new RuntimeException("Não é possível cadastrar técnico para usuário inativo.");
        }

        if (usuario.getPerfil() != PerfilUsuario.TECNICO) {
            throw new RuntimeException("O usuário informado não possui perfil de TÉCNICO.");
        }

        if (tecnicoRepository.existsByUsuarioId(usuario.getId())) {
            throw new RuntimeException("Este usuário já está vinculado a um técnico.");
        }

        Tecnico tecnico = new Tecnico(usuario, dto.getEspecialidade());

        return tecnicoRepository.save(tecnico);
    }

    public List<Tecnico> listarTodos() {
        return tecnicoRepository.findAll();
    }

    public List<Tecnico> listarAtivos() {
        return tecnicoRepository.findByAtivoTrue();
    }

    public Tecnico buscarPorId(Long id) {
        return tecnicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Técnico não encontrado com id: " + id));
    }

    public Tecnico buscarPorUsuarioId(Long usuarioId) {
        return tecnicoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Técnico não encontrado para o usuário id: " + usuarioId));
    }

    public Tecnico inativar(Long id) {
        Tecnico tecnico = buscarPorId(id);
        tecnico.setAtivo(false);
        return tecnicoRepository.save(tecnico);
    }

    public Tecnico atualizarEspecialidade(Long id, String especialidade) {
        Tecnico tecnico = buscarPorId(id);
        tecnico.setEspecialidade(especialidade);
        return tecnicoRepository.save(tecnico);
    }
}