import ResourcePage from "../components/ResourcePage";

export default function OrdensServico() {
  return (
    <ResourcePage
      title="Ordens de Serviço"
      description="Abertura e acompanhamento das ordens de serviço."
      listEndpoint="/ordens-servico"
      createEndpoint="/ordens-servico"
      loadOptions={{ clientes: "/clientes", equipamentos: "/equipamentos", tecnicos: "/tecnicos" }}
      fields={[
        { name: "clienteId", label: "Cliente", type: "select", optionsKey: "clientes", optionValue: (c) => c.id, optionLabel: (c) => c.usuario?.nome || c.nome || `Cliente ${c.id}` },
        { name: "equipamentoId", label: "Equipamento", type: "select", optionsKey: "equipamentos", optionValue: (e) => e.equipamentoId, optionLabel: (e) => `${e.tipo} ${e.marca} ${e.modelo}` },
        { name: "tecnicoId", label: "Técnico", type: "select", optionsKey: "tecnicos", optionValue: (t) => t.id, optionLabel: (t) => t.usuario?.nome || t.nome || `Técnico ${t.id}`, optional: true },
        { name: "descricaoProblema", label: "Descrição do problema", type: "textarea" },
      ]}
      columns={[
        { key: "ordemServicoId", label: "ID" },
        { key: "nomeCliente", label: "Cliente" },
        { key: "equipamento", label: "Equipamento" },
        { key: "nomeTecnico", label: "Técnico" },
        { key: "status", label: "Status", type: "status" },
        { key: "valorTotal", label: "Valor", type: "currency" },
        { key: "dataAbertura", label: "Abertura", type: "date" },
        { label: "Detalhe", type: "link", render: (item) => ({ to: `/ordens-servico/${item.ordemServicoId}`, label: "Abrir" }) },
      ]}
      actions={[
        { label: "Finalizar", endpoint: (item) => `/ordens-servico/${item.ordemServicoId}/finalizar` },
        { label: "Cancelar", endpoint: (item) => `/ordens-servico/${item.ordemServicoId}/cancelar`, danger: true },
      ]}
    />
  );
}
