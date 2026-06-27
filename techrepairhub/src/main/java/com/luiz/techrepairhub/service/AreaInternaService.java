package com.luiz.techrepairhub.service;

import com.luiz.techrepairhub.dto.MenuInternoDTO;
import com.luiz.techrepairhub.entity.PerfilUsuario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AreaInternaService {

    public List<MenuInternoDTO> montarMenuPorPerfil(PerfilUsuario perfil) {
        return switch (perfil) {
            case ADMIN -> menuAdmin();
            case GESTOR -> menuGestor();
            case ATENDENTE -> menuAtendente();
            case TECNICO -> menuTecnico();
            case CLIENTE -> menuCliente();
        };
    }

    private List<MenuInternoDTO> menuAdmin() {
        List<MenuInternoDTO> menu = new ArrayList<>();

        menu.add(new MenuInternoDTO("Dashboard", "/dashboard", "dashboard", "Visão geral do sistema."));
        menu.add(new MenuInternoDTO("Usuários", "/usuarios", "users", "Gerenciamento de usuários e perfis."));
        menu.add(new MenuInternoDTO("Clientes", "/clientes", "clientes", "Cadastro e consulta de clientes."));
        menu.add(new MenuInternoDTO("Técnicos", "/tecnicos", "tecnicos", "Cadastro e controle de técnicos."));
        menu.add(new MenuInternoDTO("Produtos", "/produtos", "produtos", "Cadastro de produtos usados."));
        menu.add(new MenuInternoDTO("Categorias", "/categorias", "categorias", "Organização dos produtos."));
        menu.add(new MenuInternoDTO("Estoque", "/estoques", "estoque", "Controle de estoque dos produtos."));
        menu.add(new MenuInternoDTO("Peças", "/pecas", "pecas", "Controle de peças usadas em reparos."));
        menu.add(new MenuInternoDTO("Pedidos", "/pedidos", "pedidos", "Acompanhamento de pedidos de venda."));
        menu.add(new MenuInternoDTO("Ordens de Serviço", "/ordens-servico", "os", "Gestão dos serviços técnicos."));
        menu.add(new MenuInternoDTO("Orçamentos", "/orcamentos", "orcamentos", "Controle de orçamentos das OS."));
        menu.add(new MenuInternoDTO("Relatórios Técnicos", "/relatorios-tecnicos", "relatorio-tecnico", "Relatórios dos serviços realizados."));
        menu.add(new MenuInternoDTO("Relatórios", "/relatorios", "relatorios", "Relatórios internos da aplicação."));
        menu.add(new MenuInternoDTO("Configurações", "/configuracoes", "config", "Configurações gerais do sistema."));

        return menu;
    }

    private List<MenuInternoDTO> menuGestor() {
        List<MenuInternoDTO> menu = new ArrayList<>();

        menu.add(new MenuInternoDTO("Dashboard", "/dashboard", "dashboard", "Indicadores gerenciais."));
        menu.add(new MenuInternoDTO("Clientes", "/clientes", "clientes", "Consulta de clientes."));
        menu.add(new MenuInternoDTO("Técnicos", "/tecnicos", "tecnicos", "Consulta de técnicos."));
        menu.add(new MenuInternoDTO("Produtos", "/produtos", "produtos", "Consulta e gestão de produtos."));
        menu.add(new MenuInternoDTO("Estoque", "/estoques", "estoque", "Controle de estoque."));
        menu.add(new MenuInternoDTO("Peças", "/pecas", "pecas", "Controle de peças."));
        menu.add(new MenuInternoDTO("Pedidos", "/pedidos", "pedidos", "Acompanhamento de pedidos."));
        menu.add(new MenuInternoDTO("Ordens de Serviço", "/ordens-servico", "os", "Acompanhamento das OS."));
        menu.add(new MenuInternoDTO("Orçamentos", "/orcamentos", "orcamentos", "Acompanhamento de orçamentos."));
        menu.add(new MenuInternoDTO("Relatórios", "/relatorios", "relatorios", "Relatórios administrativos."));

        return menu;
    }

    private List<MenuInternoDTO> menuAtendente() {
        List<MenuInternoDTO> menu = new ArrayList<>();

        menu.add(new MenuInternoDTO("Clientes", "/clientes", "clientes", "Cadastro e atendimento de clientes."));
        menu.add(new MenuInternoDTO("Produtos", "/produtos", "produtos", "Consulta de produtos disponíveis."));
        menu.add(new MenuInternoDTO("Pedidos", "/pedidos", "pedidos", "Criação e acompanhamento de pedidos."));
        menu.add(new MenuInternoDTO("Equipamentos", "/equipamentos", "equipamentos", "Cadastro de equipamentos dos clientes."));
        menu.add(new MenuInternoDTO("Ordens de Serviço", "/ordens-servico", "os", "Abertura e consulta de OS."));
        menu.add(new MenuInternoDTO("Orçamentos", "/orcamentos", "orcamentos", "Consulta de orçamentos."));
        menu.add(new MenuInternoDTO("Garantias", "/garantias", "garantias", "Consulta de garantias."));
        menu.add(new MenuInternoDTO("Relatórios", "/relatorios", "relatorios", "Relatórios operacionais."));

        return menu;
    }

    private List<MenuInternoDTO> menuTecnico() {
        List<MenuInternoDTO> menu = new ArrayList<>();

        menu.add(new MenuInternoDTO("Minhas OS", "/ordens-servico/minhas", "os", "Ordens de serviço atribuídas ao técnico."));
        menu.add(new MenuInternoDTO("Peças", "/pecas", "pecas", "Consulta de peças disponíveis."));
        menu.add(new MenuInternoDTO("Peças Utilizadas", "/pecas-utilizadas", "pecas-utilizadas", "Registro de peças utilizadas em OS."));
        menu.add(new MenuInternoDTO("Histórico da OS", "/historicos-ordem-servico", "historico", "Linha do tempo das ordens de serviço."));
        menu.add(new MenuInternoDTO("Relatórios Técnicos", "/relatorios-tecnicos", "relatorio-tecnico", "Criação e consulta de relatórios técnicos."));

        return menu;
    }

    private List<MenuInternoDTO> menuCliente() {
        List<MenuInternoDTO> menu = new ArrayList<>();

        menu.add(new MenuInternoDTO("Meus Pedidos", "/pedidos/meus", "pedidos", "Acompanhamento dos pedidos do cliente."));
        menu.add(new MenuInternoDTO("Minhas OS", "/ordens-servico/minhas", "os", "Acompanhamento das ordens de serviço."));
        menu.add(new MenuInternoDTO("Meus Equipamentos", "/equipamentos/meus", "equipamentos", "Equipamentos cadastrados."));
        menu.add(new MenuInternoDTO("Garantias", "/garantias/minhas", "garantias", "Consulta de garantias."));
        menu.add(new MenuInternoDTO("Relatórios Técnicos", "/relatorios-tecnicos", "relatorio-tecnico", "Consulta dos relatórios técnicos das OS."));

        return menu;
    }
}