export const statusLabels = {
  ATIVO: "Ativo",
  INATIVO: "Inativo",
  ABERTA: "Aberta",
  EM_ANALISE: "Em análise",
  AGUARDANDO_ORCAMENTO: "Aguardando orçamento",
  AGUARDANDO_APROVACAO: "Aguardando aprovação",
  EM_EXECUCAO: "Em execução",
  FINALIZADA: "Finalizada",
  CANCELADA: "Cancelada",
  APROVADO: "Aprovado",
  RECUSADO: "Recusado",
  PENDENTE: "Pendente",
  PAGO: "Pago",
};

export function statusLabel(status) {
  return statusLabels[status] || status || "-";
}
