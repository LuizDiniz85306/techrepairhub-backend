import ResourcePage from "../components/ResourcePage";

export default function Categorias() {
  return (
    <ResourcePage
      title="Categorias"
      description="Cadastro e consulta de categorias de produtos."
      listEndpoint="/categorias"
      createEndpoint="/categorias"
      fields={[
        { name: "nome", label: "Nome" },
        { name: "descricao", label: "Descrição", type: "textarea", optional: true },
      ]}
      columns={[
        { key: "id", label: "ID" },
        { key: "nome", label: "Nome" },
        { key: "descricao", label: "Descrição" },
        { key: "ativo", label: "Ativo", type: "boolean" },
      ]}
      actions={[
        { label: "Inativar", endpoint: (item) => `/categorias/${item.id}/inativar`, danger: true },
        { label: "Reativar", endpoint: (item) => `/categorias/${item.id}/reativar` },
      ]}
    />
  );
}
