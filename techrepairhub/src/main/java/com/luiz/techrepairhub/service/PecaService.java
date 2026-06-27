package com.luiz.techrepairhub.service;

import com.luiz.techrepairhub.dto.*;
import com.luiz.techrepairhub.entity.MovimentacaoPeca;
import com.luiz.techrepairhub.entity.Peca;
import com.luiz.techrepairhub.entity.TipoMovimentacaoPeca;
import com.luiz.techrepairhub.repository.MovimentacaoPecaRepository;
import com.luiz.techrepairhub.repository.PecaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PecaService {

    private final PecaRepository pecaRepository;
    private final MovimentacaoPecaRepository movimentacaoPecaRepository;

    public PecaService(
            PecaRepository pecaRepository,
            MovimentacaoPecaRepository movimentacaoPecaRepository
    ) {
        this.pecaRepository = pecaRepository;
        this.movimentacaoPecaRepository = movimentacaoPecaRepository;
    }

    @Transactional
    public PecaResumoDTO cadastrar(PecaCadastroDTO dto) {
        Peca peca = new Peca(
                dto.getNome(),
                dto.getDescricao(),
                dto.getPrecoCusto(),
                dto.getPrecoVenda(),
                dto.getQuantidadeEstoque(),
                dto.getEstoqueMinimo()
        );

        Peca pecaSalva = pecaRepository.save(peca);

        if (pecaSalva.getQuantidadeEstoque() > 0) {
            MovimentacaoPeca movimentacao = new MovimentacaoPeca(
                    pecaSalva,
                    TipoMovimentacaoPeca.ENTRADA,
                    pecaSalva.getQuantidadeEstoque(),
                    0,
                    pecaSalva.getQuantidadeEstoque(),
                    "Estoque inicial da peça."
            );

            movimentacaoPecaRepository.save(movimentacao);
        }

        return montarResumo(pecaSalva);
    }

    public List<PecaResumoDTO> listarTodas() {
        return pecaRepository.findAll()
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    public List<PecaResumoDTO> listarAtivas() {
        return pecaRepository.findByAtivoTrue()
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    public PecaResumoDTO buscarPorId(Long id) {
        return montarResumo(buscarEntidadePorId(id));
    }

    public List<PecaResumoDTO> buscarPorNome(String nome) {
        return pecaRepository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(this::montarResumo)
                .toList();
    }

    public List<PecaResumoDTO> listarEstoqueBaixo() {
        return pecaRepository.findByAtivoTrue()
                .stream()
                .filter(Peca::estoqueBaixo)
                .map(this::montarResumo)
                .toList();
    }

    @Transactional
    public PecaResumoDTO atualizar(Long id, PecaAtualizarDTO dto) {
        Peca peca = buscarEntidadePorId(id);

        peca.setNome(dto.getNome());
        peca.setDescricao(dto.getDescricao());
        peca.setPrecoCusto(dto.getPrecoCusto());
        peca.setPrecoVenda(dto.getPrecoVenda());
        peca.setEstoqueMinimo(dto.getEstoqueMinimo());

        return montarResumo(pecaRepository.save(peca));
    }

    @Transactional
    public PecaResumoDTO inativar(Long id) {
        Peca peca = buscarEntidadePorId(id);
        peca.setAtivo(false);
        return montarResumo(pecaRepository.save(peca));
    }

    @Transactional
    public PecaResumoDTO reativar(Long id) {
        Peca peca = buscarEntidadePorId(id);
        peca.setAtivo(true);
        return montarResumo(pecaRepository.save(peca));
    }

    @Transactional
    public PecaResumoDTO movimentarEstoque(Long id, MovimentacaoPecaDTO dto) {
        Peca peca = buscarEntidadePorId(id);

        if (!peca.getAtivo()) {
            throw new RuntimeException("Não é possível movimentar estoque de peça inativa.");
        }

        Integer quantidadeAnterior = peca.getQuantidadeEstoque();
        Integer quantidadeNova;

        if (dto.getTipoMovimentacao() == TipoMovimentacaoPeca.ENTRADA) {
            peca.adicionarEstoque(dto.getQuantidade());
            quantidadeNova = peca.getQuantidadeEstoque();
        } else if (dto.getTipoMovimentacao() == TipoMovimentacaoPeca.SAIDA) {
            peca.removerEstoque(dto.getQuantidade());
            quantidadeNova = peca.getQuantidadeEstoque();
        } else if (dto.getTipoMovimentacao() == TipoMovimentacaoPeca.AJUSTE) {
            peca.ajustarEstoque(dto.getQuantidade());
            quantidadeNova = peca.getQuantidadeEstoque();
        } else {
            throw new RuntimeException("Tipo de movimentação inválido.");
        }

        Peca pecaSalva = pecaRepository.save(peca);

        MovimentacaoPeca movimentacao = new MovimentacaoPeca(
                pecaSalva,
                dto.getTipoMovimentacao(),
                dto.getQuantidade(),
                quantidadeAnterior,
                quantidadeNova,
                dto.getObservacao()
        );

        movimentacaoPecaRepository.save(movimentacao);

        return montarResumo(pecaSalva);
    }

    public List<MovimentacaoPecaResumoDTO> listarMovimentacoes(Long pecaId) {
        return movimentacaoPecaRepository.findByPecaIdOrderByDataMovimentacaoDesc(pecaId)
                .stream()
                .map(this::montarResumoMovimentacao)
                .toList();
    }

    public Peca buscarEntidadePorId(Long id) {
        return pecaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Peça não encontrada com id: " + id));
    }

    private PecaResumoDTO montarResumo(Peca peca) {
        return new PecaResumoDTO(
                peca.getId(),
                peca.getNome(),
                peca.getDescricao(),
                peca.getPrecoCusto(),
                peca.getPrecoVenda(),
                peca.getQuantidadeEstoque(),
                peca.getEstoqueMinimo(),
                peca.estoqueBaixo(),
                peca.getAtivo(),
                peca.getDataCadastro(),
                peca.getDataAtualizacao()
        );
    }

    private MovimentacaoPecaResumoDTO montarResumoMovimentacao(MovimentacaoPeca movimentacao) {
        return new MovimentacaoPecaResumoDTO(
                movimentacao.getId(),
                movimentacao.getPeca().getId(),
                movimentacao.getPeca().getNome(),
                movimentacao.getTipoMovimentacao(),
                movimentacao.getQuantidade(),
                movimentacao.getQuantidadeAnterior(),
                movimentacao.getQuantidadeNova(),
                movimentacao.getDataMovimentacao(),
                movimentacao.getObservacao()
        );
    }
}
