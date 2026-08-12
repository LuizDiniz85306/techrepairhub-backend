import ResourcePage from "../components/ResourcePage";

export default function Estoque() {
  return (
    <ResourcePage
      title="Estoque"
      description="Cadastro e acompanhamento do estoque de produtos."
      listEndpoint="/estoques"
      createEndpoint="/estoques"
      loadOptions={{ produtos: "/produtos" }}
      fields={[
        {
          name: "produtoId",
          label: "Produto",
          type: "select",
          optionsKey: "produtos",
          optionValue: (produto) => produto.id,
          optionLabel: (produto) => produto.nome,
        },
        { name: "quantidadeAtual", label: "Quantidade atual", type: "number", min: "0" },
        { name: "estoqueMinimo", label: "Estoque mínimo", type: "number", min: "0" },
      ]}
      columns={[
        { key: "id", label: "ID" },
        { key: "produto.nome", label: "Produto" },
        { key: "quantidadeAtual", label: "Quantidade" },
        { key: "estoqueMinimo", label: "Mínimo" },
        { key: "dataAtualizacao", label: "Atualizado em", type: "date" },
        { key: "ativo", label: "Ativo", type: "boolean" },
      ]}
      actions={[
        { label: "Inativar", endpoint: (item) => `/estoques/${item.id}/inativar`, danger: true },
        { label: "Reativar", endpoint: (item) => `/estoques/${item.id}/reativar` },
      ]}
    />
  );
}
