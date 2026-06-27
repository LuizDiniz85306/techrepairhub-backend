package com.luiz.techrepairhub.service;

import com.luiz.techrepairhub.dto.relatorio.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelatorioService {

    private final JdbcTemplate jdbcTemplate;

    public RelatorioService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ProdutoCategoriaEstoqueRelatorioDTO> produtosComCategoriaEEstoque() {
        String sql = """
                SELECT 
                    p.id AS produto_id,
                    p.nome AS produto,
                    c.nome AS categoria,
                    p.preco,
                    e.quantidade_atual,
                    e.estoque_minimo
                FROM produtos p
                JOIN categorias c ON p.categoria_id = c.id
                JOIN estoques e ON e.produto_id = p.id
                ORDER BY p.nome
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new ProdutoCategoriaEstoqueRelatorioDTO(
                        rs.getLong("produto_id"),
                        rs.getString("produto"),
                        rs.getString("categoria"),
                        rs.getBigDecimal("preco"),
                        rs.getInt("quantidade_atual"),
                        rs.getInt("estoque_minimo")
                )
        );
    }

    public List<PedidoClienteRelatorioDTO> pedidosComDadosCliente() {
        String sql = """
                SELECT 
                    p.id AS pedido_id,
                    u.nome AS cliente,
                    p.status,
                    p.valor_total,
                    p.data_pedido
                FROM pedidos p
                JOIN clientes c ON p.cliente_id = c.id
                JOIN usuarios u ON c.usuario_id = u.id
                ORDER BY p.data_pedido DESC
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new PedidoClienteRelatorioDTO(
                        rs.getLong("pedido_id"),
                        rs.getString("cliente"),
                        rs.getString("status"),
                        rs.getBigDecimal("valor_total"),
                        rs.getTimestamp("data_pedido").toLocalDateTime()
                )
        );
    }

    public List<OrdemServicoRelatorioDTO> ordensServicoComClienteEquipamentoTecnico() {
        String sql = """
                SELECT 
                    os.id AS ordem_servico_id,
                    uc.nome AS cliente,
                    e.tipo AS tipo_equipamento,
                    e.marca,
                    e.modelo,
                    ut.nome AS tecnico,
                    os.status,
                    os.valor_total,
                    os.data_abertura
                FROM ordens_servico os
                JOIN clientes c ON os.cliente_id = c.id
                JOIN usuarios uc ON c.usuario_id = uc.id
                JOIN equipamentos e ON os.equipamento_id = e.id
                LEFT JOIN tecnicos t ON os.tecnico_id = t.id
                LEFT JOIN usuarios ut ON t.usuario_id = ut.id
                ORDER BY os.data_abertura DESC
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new OrdemServicoRelatorioDTO(
                        rs.getLong("ordem_servico_id"),
                        rs.getString("cliente"),
                        rs.getString("tipo_equipamento"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getString("tecnico"),
                        rs.getString("status"),
                        rs.getBigDecimal("valor_total"),
                        rs.getTimestamp("data_abertura").toLocalDateTime()
                )
        );
    }

    public List<ProdutoEstoqueAbaixoMediaRelatorioDTO> produtosEstoqueAbaixoMedia() {
        String sql = """
                SELECT 
                    p.id AS produto_id,
                    p.nome AS produto,
                    e.quantidade_atual
                FROM produtos p
                JOIN estoques e ON e.produto_id = p.id
                WHERE e.quantidade_atual < (
                    SELECT AVG(e2.quantidade_atual)
                    FROM estoques e2
                )
                ORDER BY e.quantidade_atual ASC
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new ProdutoEstoqueAbaixoMediaRelatorioDTO(
                        rs.getLong("produto_id"),
                        rs.getString("produto"),
                        rs.getInt("quantidade_atual")
                )
        );
    }

    public List<PedidoAcimaMediaRelatorioDTO> pedidosValorAcimaMedia() {
        String sql = """
                SELECT 
                    p.id AS pedido_id,
                    p.valor_total,
                    p.status,
                    p.data_pedido
                FROM pedidos p
                WHERE p.valor_total > (
                    SELECT AVG(p2.valor_total)
                    FROM pedidos p2
                )
                ORDER BY p.valor_total DESC
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new PedidoAcimaMediaRelatorioDTO(
                        rs.getLong("pedido_id"),
                        rs.getBigDecimal("valor_total"),
                        rs.getString("status"),
                        rs.getTimestamp("data_pedido").toLocalDateTime()
                )
        );
    }

    public List<ClienteComPedidoRelatorioDTO> clientesQuePossuemPedidos() {
        String sql = """
                SELECT 
                    c.id AS cliente_id,
                    u.nome AS cliente
                FROM clientes c
                JOIN usuarios u ON c.usuario_id = u.id
                WHERE c.id IN (
                    SELECT p.cliente_id
                    FROM pedidos p
                )
                ORDER BY u.nome
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new ClienteComPedidoRelatorioDTO(
                        rs.getLong("cliente_id"),
                        rs.getString("cliente")
                )
        );
    }

    public List<TotalVendidoClienteRelatorioDTO> totalVendidoPorCliente() {
        String sql = """
                SELECT 
                    c.id AS cliente_id,
                    u.nome AS cliente,
                    COUNT(p.id) AS quantidade_pedidos,
                    SUM(p.valor_total) AS total_gasto
                FROM clientes c
                JOIN usuarios u ON c.usuario_id = u.id
                JOIN pedidos p ON p.cliente_id = c.id
                GROUP BY c.id, u.nome
                ORDER BY total_gasto DESC
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new TotalVendidoClienteRelatorioDTO(
                        rs.getLong("cliente_id"),
                        rs.getString("cliente"),
                        rs.getLong("quantidade_pedidos"),
                        rs.getBigDecimal("total_gasto")
                )
        );
    }

    public List<OrdemPorTecnicoRelatorioDTO> quantidadeOrdensPorTecnico() {
        String sql = """
                SELECT 
                    t.id AS tecnico_id,
                    u.nome AS tecnico,
                    COUNT(os.id) AS quantidade_ordens
                FROM tecnicos t
                JOIN usuarios u ON t.usuario_id = u.id
                LEFT JOIN ordens_servico os ON os.tecnico_id = t.id
                GROUP BY t.id, u.nome
                ORDER BY quantidade_ordens DESC
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new OrdemPorTecnicoRelatorioDTO(
                        rs.getLong("tecnico_id"),
                        rs.getString("tecnico"),
                        rs.getLong("quantidade_ordens")
                )
        );
    }
}