import { useLocation } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

const titles = [
  ["/dashboard", "Dashboard"],
  ["/clientes", "Clientes"],
  ["/usuarios", "Usuários"],
  ["/tecnicos", "Técnicos"],
  ["/categorias", "Categorias"],
  ["/produtos", "Produtos"],
  ["/estoque", "Estoque"],
  ["/movimentacoes-estoque", "Movimentações de Estoque"],
  ["/pedidos", "Pedidos"],
  ["/garantias", "Garantias"],
  ["/equipamentos", "Equipamentos"],
  ["/ordens-servico", "Ordens de Serviço"],
  ["/orcamentos", "Orçamentos"],
  ["/pecas-utilizadas", "Peças Utilizadas"],
  ["/pecas", "Peças"],
  ["/relatorios-tecnicos", "Relatórios Técnicos"],
  ["/relatorios", "Relatórios Internos"],
  ["/financeiro/receitas", "Receitas"],
  ["/financeiro/despesas", "Despesas"],
  ["/financeiro/fluxo-caixa", "Fluxo de Caixa"],
  ["/financeiro", "Financeiro"],
  ["/permissoes", "Permissões"],
  ["/tecnico/minhas-os", "Minhas OS"],
  ["/tecnico/relatorios", "Relatórios do Técnico"],
  ["/tecnico", "Área do Técnico"],
  ["/cliente/equipamentos", "Meus Equipamentos"],
  ["/cliente/ordens-servico", "Minhas Ordens de Serviço"],
  ["/cliente", "Área do Cliente"],
];

function currentTitle(pathname) {
  const match = titles.find(([path]) => pathname === path || pathname.startsWith(`${path}/`));
  return match?.[1] || "Área Interna";
}

export default function Header() {
  const { nome, perfil, logout } = useAuth();
  const { pathname } = useLocation();

  return (
    <header className="header">
      <div>
        <h2>{currentTitle(pathname)}</h2>
        <span>Bem-vindo, {nome || "Usuário"}</span>
      </div>

      <div className="header-user">
        <strong className="profile-badge">{perfil || "PERFIL"}</strong>
        <button type="button" className="btn btn-danger" onClick={logout}>Sair</button>
      </div>
    </header>
  );
}
