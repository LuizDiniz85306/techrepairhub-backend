import ResourcePage from "../components/ResourcePage";

export default function Pecas() {
  return (
    <ResourcePage
      title="Peças"
      description="Cadastro, consulta e controle básico de peças."
      listEndpoint="/pecas"
      createEndpoint="/pecas"
      fields={[
        { name: "nome", label: "Nome" },
        { name: "descricao", label: "Descrição", type: "textarea", optional: true },
        { name: "precoCusto", label: "Preço de custo", type: "number", step: "0.01", min: "0" },
        { name: "precoVenda", label: "Preço de venda", type: "number", step: "0.01", min: "0" },
        { name: "quantidadeEstoque", label: "Quantidade em estoque", type: "number", min: "0" },
        { name: "estoqueMinimo", label: "Estoque mínimo", type: "number", min: "0" },
      ]}
      columns={[
        { key: "id", label: "ID" },
        { key: "nome", label: "Nome" },
        { key: "precoCusto", label: "Custo", type: "currency" },
        { key: "precoVenda", label: "Venda", type: "currency" },
        { key: "quantidadeEstoque", label: "Estoque" },
        { key: "estoqueMinimo", label: "Mínimo" },
        { key: "estoqueBaixo", label: "Estoque baixo", type: "boolean" },
        { key: "ativo", label: "Ativo", type: "boolean" },
      ]}
      actions={[
        { label: "Inativar", endpoint: (item) => `/pecas/${item.id}/inativar`, danger: true },
        { label: "Reativar", endpoint: (item) => `/pecas/${item.id}/reativar` },
      ]}
    />
  );
}
