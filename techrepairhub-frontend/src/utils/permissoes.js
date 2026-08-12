export const perfis = ["ADMIN", "GESTOR", "ATENDENTE", "TECNICO", "CLIENTE"];

export const permissoesFrontend = {
  ADMIN: ["*"],
  GESTOR: ["dashboard", "clientes", "tecnicos", "produtos", "estoque", "pedidos", "ordens-servico", "orcamentos", "pecas", "relatorios", "financeiro"],
  ATENDENTE: ["clientes", "produtos", "pedidos", "garantias", "equipamentos", "ordens-servico", "orcamentos"],
  TECNICO: ["tecnico", "ordens-servico", "pecas", "pecas-utilizadas", "relatorios-tecnicos"],
  CLIENTE: ["cliente", "cliente/equipamentos", "cliente/ordens-servico", "catalogo"],
};

export function perfilPodeAcessar(perfil, recurso) {
  const permissoes = permissoesFrontend[perfil] || [];
  return permissoes.includes("*") || permissoes.includes(recurso);
}
