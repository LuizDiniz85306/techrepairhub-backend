import ResourcePage from "../components/ResourcePage";
import { TIPOS_MOVIMENTACAO } from "../config/options";

export default function MovimentacoesEstoque() {
  return (
    <ResourcePage
      title="Movimentações de Estoque"
      description="Registro de entradas, saídas e ajustes do estoque."
      listEndpoint="/movimentacoes-estoque"
      createEndpoint="/movimentacoes-estoque"
      loadOptions={{ produtos: "/produtos" }}
      fields={[
        { name: "produtoId", label: "Produto", type: "select", optionsKey: "produtos", optionValue: (p) => p.id, optionLabel: (p) => p.nome },
        { name: "tipo", label: "Tipo", type: "select", options: TIPOS_MOVIMENTACAO },
        { name: "quantidade", label: "Quantidade", type: "number", min: "1" },
        { name: "observacao", label: "Observação", type: "textarea", optional: true },
      ]}
      columns={[
        { key: "id", label: "ID" },
        { key: "estoque.produto.nome", label: "Produto" },
        { key: "tipo", label: "Tipo", type: "status" },
        { key: "quantidade", label: "Quantidade" },
        { key: "observacao", label: "Observação" },
        { key: "dataMovimentacao", label: "Data", type: "date" },
      ]}
    />
  );
}
