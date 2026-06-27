package com.luiz.techrepairhub.service;

import com.luiz.techrepairhub.dto.PermissaoPerfilDTO;
import com.luiz.techrepairhub.entity.PerfilUsuario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissaoPerfilService {

    public PermissaoPerfilDTO buscarPermissoesPorPerfil(PerfilUsuario perfil) {
        return switch (perfil) {
            case ADMIN -> permissoesAdmin();
            case GESTOR -> permissoesGestor();
            case ATENDENTE -> permissoesAtendente();
            case TECNICO -> permissoesTecnico();
            case CLIENTE -> permissoesCliente();
        };
    }

    public List<PermissaoPerfilDTO> listarTodas() {
        return List.of(
                permissoesAdmin(),
                permissoesGestor(),
                permissoesAtendente(),
                permissoesTecnico(),
                permissoesCliente()
        );
    }

    private PermissaoPerfilDTO permissoesAdmin() {
        return new PermissaoPerfilDTO(
                "ADMIN",
                "Administrador geral do sistema. Possui acesso completo.",
                List.of(
                        "Gerenciar usuários",
                        "Gerenciar técnicos",
                        "Gerenciar clientes",
                        "Gerenciar produtos",
                        "Gerenciar estoque",
                        "Gerenciar pedidos",
                        "Gerenciar ordens de serviço",
                        "Gerenciar orçamentos",
                        "Gerenciar peças",
                        "Gerenciar relatórios técnicos",
                        "Consultar relatórios",
                        "Acessar configurações"
                ),
                List.of(
                        "Dashboard",
                        "Usuários",
                        "Clientes",
                        "Técnicos",
                        "Produtos",
                        "Categorias",
                        "Estoque",
                        "Movimentações de Estoque",
                        "Carrinhos",
                        "Pedidos",
                        "Garantias",
                        "Equipamentos",
                        "Ordens de Serviço",
                        "Histórico da OS",
                        "Orçamentos",
                        "Peças",
                        "Peças Utilizadas",
                        "Relatórios Técnicos",
                        "Relatórios Internos",
                        "Área Interna",
                        "Configurações"
                ),
                List.of(
                        "/usuarios/**",
                        "/tecnicos/**",
                        "/clientes/**",
                        "/categorias/**",
                        "/produtos/**",
                        "/imagens-produto/**",
                        "/especificacoes-produto/**",
                        "/estoques/**",
                        "/movimentacoes-estoque/**",
                        "/carrinhos/**",
                        "/pedidos/**",
                        "/garantias/**",
                        "/equipamentos/**",
                        "/ordens-servico/**",
                        "/historicos-ordem-servico/**",
                        "/orcamentos/**",
                        "/pecas/**",
                        "/pecas-utilizadas/**",
                        "/relatorios-tecnicos/**",
                        "/relatorios/**",
                        "/area-interna/**",
                        "/permissoes/**"
                )
        );
    }

    private PermissaoPerfilDTO permissoesGestor() {
        return new PermissaoPerfilDTO(
                "GESTOR",
                "Usuário responsável pela gestão operacional e acompanhamento de indicadores.",
                List.of(
                        "Acessar dashboard",
                        "Consultar clientes",
                        "Consultar técnicos",
                        "Gerenciar produtos",
                        "Gerenciar estoque",
                        "Consultar pedidos",
                        "Consultar ordens de serviço",
                        "Consultar orçamentos",
                        "Gerenciar peças",
                        "Consultar relatórios",
                        "Consultar relatórios técnicos"
                ),
                List.of(
                        "Dashboard",
                        "Clientes",
                        "Técnicos",
                        "Produtos",
                        "Categorias",
                        "Estoque",
                        "Movimentações de Estoque",
                        "Pedidos",
                        "Garantias",
                        "Equipamentos",
                        "Ordens de Serviço",
                        "Histórico da OS",
                        "Orçamentos",
                        "Peças",
                        "Peças Utilizadas",
                        "Relatórios Técnicos",
                        "Relatórios Internos",
                        "Área Interna"
                ),
                List.of(
                        "/clientes/**",
                        "/tecnicos/**",
                        "/categorias/**",
                        "/produtos/**",
                        "/imagens-produto/**",
                        "/especificacoes-produto/**",
                        "/estoques/**",
                        "/movimentacoes-estoque/**",
                        "/pedidos/**",
                        "/garantias/**",
                        "/equipamentos/**",
                        "/ordens-servico/**",
                        "/historicos-ordem-servico/**",
                        "/orcamentos/**",
                        "/pecas/**",
                        "/pecas-utilizadas/**",
                        "/relatorios-tecnicos/**",
                        "/relatorios/**",
                        "/area-interna/**",
                        "/permissoes/**"
                )
        );
    }

    private PermissaoPerfilDTO permissoesAtendente() {
        return new PermissaoPerfilDTO(
                "ATENDENTE",
                "Usuário responsável por atendimento, cadastro de clientes, pedidos e abertura de OS.",
                List.of(
                        "Cadastrar clientes",
                        "Consultar clientes",
                        "Consultar produtos",
                        "Criar pedidos",
                        "Consultar pedidos",
                        "Cadastrar equipamentos",
                        "Abrir ordens de serviço",
                        "Consultar ordens de serviço",
                        "Consultar garantias",
                        "Consultar orçamentos",
                        "Consultar relatórios operacionais"
                ),
                List.of(
                        "Clientes",
                        "Produtos",
                        "Categorias",
                        "Pedidos",
                        "Garantias",
                        "Equipamentos",
                        "Ordens de Serviço",
                        "Histórico da OS",
                        "Orçamentos",
                        "Relatórios Internos",
                        "Área Interna"
                ),
                List.of(
                        "/clientes/**",
                        "/categorias/**",
                        "/produtos/**",
                        "/imagens-produto/**",
                        "/especificacoes-produto/**",
                        "/pedidos/**",
                        "/garantias/**",
                        "/equipamentos/**",
                        "/ordens-servico/**",
                        "/historicos-ordem-servico/**",
                        "/orcamentos/**",
                        "/relatorios/**",
                        "/area-interna/**",
                        "/permissoes/**"
                )
        );
    }

    private PermissaoPerfilDTO permissoesTecnico() {
        return new PermissaoPerfilDTO(
                "TECNICO",
                "Usuário responsável por executar serviços técnicos e registrar informações da OS.",
                List.of(
                        "Consultar OS atribuídas",
                        "Atualizar andamento da OS",
                        "Consultar histórico da OS",
                        "Consultar peças",
                        "Registrar peças utilizadas",
                        "Criar relatório técnico",
                        "Atualizar relatório técnico"
                ),
                List.of(
                        "Ordens de Serviço",
                        "Histórico da OS",
                        "Orçamentos",
                        "Peças",
                        "Peças Utilizadas",
                        "Relatórios Técnicos",
                        "Área Interna"
                ),
                List.of(
                        "/equipamentos/**",
                        "/ordens-servico/**",
                        "/historicos-ordem-servico/**",
                        "/orcamentos/**",
                        "/pecas/**",
                        "/pecas-utilizadas/**",
                        "/relatorios-tecnicos/**",
                        "/area-interna/**",
                        "/permissoes/**"
                )
        );
    }

    private PermissaoPerfilDTO permissoesCliente() {
        return new PermissaoPerfilDTO(
                "CLIENTE",
                "Usuário final que acompanha pedidos, garantias e ordens de serviço.",
                List.of(
                        "Consultar catálogo",
                        "Gerenciar carrinho",
                        "Consultar próprios pedidos",
                        "Consultar próprias garantias",
                        "Consultar próprios equipamentos",
                        "Acompanhar próprias ordens de serviço",
                        "Consultar histórico da própria OS",
                        "Consultar relatório técnico da própria OS"
                ),
                List.of(
                        "Catálogo",
                        "Carrinho",
                        "Pedidos",
                        "Garantias",
                        "Equipamentos",
                        "Ordens de Serviço",
                        "Histórico da OS",
                        "Relatórios Técnicos",
                        "Área Interna"
                ),
                List.of(
                        "/categorias/**",
                        "/produtos/**",
                        "/imagens-produto/**",
                        "/especificacoes-produto/**",
                        "/carrinhos/**",
                        "/pedidos/**",
                        "/garantias/**",
                        "/equipamentos/**",
                        "/ordens-servico/**",
                        "/historicos-ordem-servico/**",
                        "/relatorios-tecnicos/**",
                        "/area-interna/**",
                        "/permissoes/**"
                )
        );
    }
}