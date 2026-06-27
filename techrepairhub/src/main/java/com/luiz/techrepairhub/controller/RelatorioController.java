package com.luiz.techrepairhub.controller;

import com.luiz.techrepairhub.dto.relatorio.*;
import com.luiz.techrepairhub.service.RelatorioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/produtos-categoria-estoque")
    public List<ProdutoCategoriaEstoqueRelatorioDTO> produtosComCategoriaEEstoque() {
        return relatorioService.produtosComCategoriaEEstoque();
    }

    @GetMapping("/pedidos-clientes")
    public List<PedidoClienteRelatorioDTO> pedidosComDadosCliente() {
        return relatorioService.pedidosComDadosCliente();
    }

    @GetMapping("/ordens-servico")
    public List<OrdemServicoRelatorioDTO> ordensServicoComClienteEquipamentoTecnico() {
        return relatorioService.ordensServicoComClienteEquipamentoTecnico();
    }

    @GetMapping("/produtos-estoque-abaixo-media")
    public List<ProdutoEstoqueAbaixoMediaRelatorioDTO> produtosEstoqueAbaixoMedia() {
        return relatorioService.produtosEstoqueAbaixoMedia();
    }

    @GetMapping("/pedidos-acima-media")
    public List<PedidoAcimaMediaRelatorioDTO> pedidosValorAcimaMedia() {
        return relatorioService.pedidosValorAcimaMedia();
    }

    @GetMapping("/clientes-com-pedidos")
    public List<ClienteComPedidoRelatorioDTO> clientesQuePossuemPedidos() {
        return relatorioService.clientesQuePossuemPedidos();
    }

    @GetMapping("/total-vendido-cliente")
    public List<TotalVendidoClienteRelatorioDTO> totalVendidoPorCliente() {
        return relatorioService.totalVendidoPorCliente();
    }

    @GetMapping("/ordens-por-tecnico")
    public List<OrdemPorTecnicoRelatorioDTO> quantidadeOrdensPorTecnico() {
        return relatorioService.quantidadeOrdensPorTecnico();
    }
}