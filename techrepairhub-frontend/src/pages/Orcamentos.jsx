import ResourcePage from "../components/ResourcePage";

export default function Orcamentos() {
  return (
    <ResourcePage
      title="Orçamentos"
      description="Criação e aprovação de orçamentos de ordens de serviço."
      listEndpoint="/orcamentos"
      createEndpoint="/orcamentos"
      loadOptions={{ ordens: "/ordens-servico" }}
      fields={[
        { name: "ordemServicoId", label: "Ordem de serviço", type: "select", optionsKey: "ordens", optionValue: (os) => os.ordemServicoId, optionLabel: (os) => `OS ${os.ordemServicoId} - ${os.nomeCliente}` },
        { name: "descricao", label: "Descrição", type: "textarea" },
        { name: "valorMaoObra", label: "Valor mão de obra", type: "number", step: "0.01", min: "0" },
        { name: "valorPecas", label: "Valor peças", type: "number", step: "0.01", min: "0" },
      ]}
      columns={[
        { key: "id", label: "ID" },
        { key: "ordemServicoId", label: "OS" },
        { key: "cliente", label: "Cliente" },
        { key: "equipamento", label: "Equipamento" },
        { key: "valorTotal", label: "Valor total", type: "currency" },
        { key: "status", label: "Status", type: "status" },
      ]}
      actions={[
        { label: "Aprovar", endpoint: (item) => `/orcamentos/${item.id}/aprovar` },
        { label: "Recusar", endpoint: (item) => `/orcamentos/${item.id}/recusar`, danger: true },
      ]}
    />
  );
}
