import ResourcePage from "../components/ResourcePage";

export default function PecasUtilizadas() {
  return (
    <ResourcePage
      title="Peças Utilizadas"
      description="Registro de peças usadas em ordens de serviço."
      listEndpoint="/pecas-utilizadas"
      createEndpoint="/pecas-utilizadas"
      loadOptions={{ ordens: "/ordens-servico", pecas: "/pecas" }}
      fields={[
        { name: "ordemServicoId", label: "Ordem de serviço", type: "select", optionsKey: "ordens", optionValue: (os) => os.ordemServicoId, optionLabel: (os) => `OS ${os.ordemServicoId} - ${os.nomeCliente}` },
        { name: "pecaId", label: "Peça", type: "select", optionsKey: "pecas", optionValue: (p) => p.id, optionLabel: (p) => p.nome },
        { name: "quantidade", label: "Quantidade", type: "number", min: "1" },
      ]}
      columns={[
        { key: "id", label: "ID" },
        { key: "ordemServicoId", label: "OS" },
        { key: "peca", label: "Peça" },
        { key: "quantidade", label: "Quantidade" },
        { key: "valorUnitario", label: "Valor unitário", type: "currency" },
        { key: "subtotal", label: "Subtotal", type: "currency" },
        { key: "dataUtilizacao", label: "Data", type: "date" },
      ]}
    />
  );
}
