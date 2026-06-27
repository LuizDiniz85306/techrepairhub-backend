package com.luiz.techrepairhub.service;

import com.luiz.techrepairhub.dto.CategoriaAtualizarDTO;
import com.luiz.techrepairhub.dto.CategoriaCadastroDTO;
import com.luiz.techrepairhub.entity.Categoria;
import com.luiz.techrepairhub.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria cadastrar(CategoriaCadastroDTO dto) {
        if (categoriaRepository.existsByNomeIgnoreCase(dto.getNome())) {
            throw new RuntimeException("Já existe uma categoria cadastrada com este nome.");
        }

        Categoria categoria = new Categoria(dto.getNome(), dto.getDescricao());

        return categoriaRepository.save(categoria);
    }

    public List<Categoria> listarTodos() {
        return categoriaRepository.findAll();
    }

    public List<Categoria> listarAtivas() {
        return categoriaRepository.findByAtivoTrue();
    }

    public Categoria buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com id: " + id));
    }

    public List<Categoria> buscarPorNome(String nome) {
        return categoriaRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Categoria atualizar(Long id, CategoriaAtualizarDTO dto) {
        Categoria categoria = buscarPorId(id);

        categoriaRepository.findByNomeIgnoreCase(dto.getNome())
                .ifPresent(categoriaExistente -> {
                    if (!categoriaExistente.getId().equals(id)) {
                        throw new RuntimeException("Já existe outra categoria com este nome.");
                    }
                });

        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());

        return categoriaRepository.save(categoria);
    }

    public Categoria inativar(Long id) {
        Categoria categoria = buscarPorId(id);
        categoria.setAtivo(false);
        return categoriaRepository.save(categoria);
    }

    public Categoria reativar(Long id) {
        Categoria categoria = buscarPorId(id);
        categoria.setAtivo(true);
        return categoriaRepository.save(categoria);
    }
}