import ResourcePage from "../components/ResourcePage";

export default function Equipamentos() {
  return (
    <ResourcePage
      title="Equipamentos"
      description="Cadastro e consulta de equipamentos de clientes."
      listEndpoint="/equipamentos"
      createEndpoint="/equipamentos"
      loadOptions={{ clientes: "/clientes" }}
      fields={[
        { name: "clienteId", label: "Cliente", type: "select", optionsKey: "clientes", optionValue: (c) => c.id, optionLabel: (c) => c.usuario?.nome || c.nome || `Cliente ${c.id}` },
        { name: "tipo", label: "Tipo" },
        { name: "marca", label: "Marca" },
        { name: "modelo", label: "Modelo" },
        { name: "numeroSerie", label: "Número de série", optional: true },
        { name: "descricao", label: "Descrição", type: "textarea", optional: true },
      ]}
      columns={[
        { key: "equipamentoId", label: "ID" },
        { key: "nomeCliente", label: "Cliente" },
        { key: "tipo", label: "Tipo" },
        { key: "marca", label: "Marca" },
        { key: "modelo", label: "Modelo" },
        { key: "numeroSerie", label: "Série" },
        { key: "ativo", label: "Ativo", type: "boolean" },
      ]}
      actions={[
        { label: "Inativar", endpoint: (item) => `/equipamentos/${item.equipamentoId}/inativar`, danger: true },
        { label: "Reativar", endpoint: (item) => `/equipamentos/${item.equipamentoId}/reativar` },
      ]}
    />
  );
}
