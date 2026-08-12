import ResourcePage from "../components/ResourcePage";

export default function Garantias() {
  return (
    <ResourcePage
      title="Garantias"
      description="Consulta de garantias geradas pelo fluxo de pedidos."
      listEndpoint="/garantias"
      createEndpoint={null}
      noCreateMessage="Funcionalidade ainda não disponível no back-end: não há POST /garantias."
      columns={[
        { key: "garantiaId", label: "ID" },
        { key: "nomeCliente", label: "Cliente" },
        { key: "nomeProduto", label: "Produto" },
        { key: "mesesGarantia", label: "Meses" },
        { key: "dataInicio", label: "Início", type: "date" },
        { key: "dataFim", label: "Fim", type: "date" },
        { key: "status", label: "Status", type: "status" },
      ]}
      actions={[
        { label: "Cancelar", endpoint: (item) => `/garantias/${item.garantiaId}/cancelar`, danger: true },
      ]}
    />
  );
}
