import { NavLink } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

const groups = [
  {
    title: "Principal",
    items: [
      ["📊", "Painel", "/dashboard", ["ADMIN", "GESTOR"]],
      ["🏠", "Área do Cliente", "/cliente", ["CLIENTE"]],
      ["🧰", "Área do Técnico", "/tecnico", ["TECNICO", "ADMIN"]],
    ],
  },
  {
    title: "Cadastros",
    items: [
      ["👥", "Usuários", "/usuarios", ["ADMIN"]],
      ["🙋", "Clientes", "/clientes", ["ADMIN", "ATENDENTE"]],
      ["🛠", "Técnicos", "/tecnicos", ["ADMIN"]],
      ["🏷", "Categorias", "/categorias", ["ADMIN", "GESTOR", "ATENDENTE"]],
      ["💻", "Produtos", "/produtos", ["ADMIN", "GESTOR", "ATENDENTE"]],
    ],
  },
  {
    title: "Operação",
    items: [
      ["🧾", "Pedidos", "/pedidos", ["ADMIN", "GESTOR", "ATENDENTE"]],
      ["🛡", "Garantias", "/garantias", ["ADMIN", "GESTOR", "ATENDENTE"]],
      ["🖥", "Equipamentos", "/equipamentos", ["ADMIN", "GESTOR", "ATENDENTE"]],
      ["🔧", "Ordens de Serviço", "/ordens-servico", ["ADMIN", "GESTOR", "ATENDENTE"]],
      ["💬", "Orçamentos", "/orcamentos", ["ADMIN", "GESTOR", "ATENDENTE"]],
      ["🔧", "Minhas OS", "/tecnico/minhas-os", ["TECNICO"]],
      ["🖥", "Meus Equipamentos", "/cliente/equipamentos", ["CLIENTE"]],
      ["📋", "Minhas OS", "/cliente/ordens-servico", ["CLIENTE"]],
      ["TR", "Meu carrinho", "/cliente/carrinho", ["CLIENTE"]],
    ],
  },
  {
    title: "Estoque",
    items: [
      ["📦", "Estoque", "/estoque", ["ADMIN", "GESTOR", "ATENDENTE"]],
      ["↕", "Movimentações", "/movimentacoes-estoque", ["ADMIN", "GESTOR"]],
      ["⚙", "Peças", "/pecas", ["ADMIN", "GESTOR", "ATENDENTE", "TECNICO"]],
      ["🧩", "Peças Utilizadas", "/pecas-utilizadas", ["ADMIN", "GESTOR", "ATENDENTE", "TECNICO"]],
    ],
  },
  {
    title: "Financeiro",
    items: [
      ["💵", "Resumo", "/financeiro", ["ADMIN", "GESTOR"]],
      ["📈", "Receitas", "/financeiro/receitas", ["ADMIN", "GESTOR"]],
      ["📉", "Despesas", "/financeiro/despesas", ["ADMIN", "GESTOR"]],
      ["💰", "Fluxo de Caixa", "/financeiro/fluxo-caixa", ["ADMIN", "GESTOR"]],
    ],
  },
  {
    title: "Relatórios",
    items: [
      ["📝", "Relatórios Técnicos", "/relatorios-tecnicos", ["ADMIN"]],
      ["📊", "Relatórios Internos", "/relatorios", ["ADMIN"]],
      ["📝", "Meus Relatórios", "/tecnico/relatorios", ["TECNICO"]],
    ],
  },
  {
    title: "Sistema",
    items: [
      ["🔐", "Permissões", "/permissoes", ["ADMIN", "GESTOR", "TECNICO"]],
      ["🛒", "Carrinho", "/carrinho", ["ADMIN", "GESTOR"]],
      ["🌐", "Catálogo Público", "/catalogo", ["ADMIN", "GESTOR", "TECNICO", "CLIENTE"]],
    ],
  },
];

export default function Sidebar() {
  const { perfil } = useAuth();

  const visibleGroups = groups
    .map((group) => ({
      ...group,
      items: group.items.filter(([, , , perfis]) => !perfil || perfis.includes(perfil)),
    }))
    .filter((group) => group.items.length > 0);

  return (
    <aside className="sidebar">
      <div className="sidebar-brand">
        <span>TR</span>
        <div>
          <h1>TechRepair Hub</h1>
          <small>Assistência e loja</small>
        </div>
      </div>

      <nav>
        {visibleGroups.map((group) => (
          <section className="sidebar-group" key={group.title}>
            <h2>{group.title}</h2>
            {group.items.map(([icon, label, to]) => (
              <NavLink key={to} to={to} className={({ isActive }) => (isActive ? "active" : "")}>
                <span className="sidebar-icon">{icon}</span>
                <span>{label}</span>
              </NavLink>
            ))}
          </section>
        ))}
      </nav>
    </aside>
  );
}
