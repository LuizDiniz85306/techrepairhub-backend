import ResourcePage from "../../components/ResourcePage";
import { FORMAS_PAGAMENTO, TIPOS_RECEITA } from "../../config/options";

export default function Receitas() {
  return (
    <ResourcePage
      title="Receitas"
      description="Lançamento e consulta de receitas."
      listEndpoint="/receitas"
      createEndpoint="/receitas"
      fields={[
        { name: "descricao", label: "Descrição" },
        { name: "valor", label: "Valor", type: "number", step: "0.01", min: "0.01" },
        { name: "tipoReceita", label: "Tipo", type: "select", options: TIPOS_RECEITA },
        { name: "formaPagamento", label: "Forma de pagamento", type: "select", options: FORMAS_PAGAMENTO },
        { name: "pedidoId", label: "Pedido ID", type: "number", optional: true },
        { name: "ordemServicoId", label: "Ordem de serviço ID", type: "number", optional: true },
        { name: "observacao", label: "Observação", type: "textarea", optional: true },
      ]}
      columns={[
        { key: "id", label: "ID" },
        { key: "descricao", label: "Descrição" },
        { key: "valor", label: "Valor", type: "currency" },
        { key: "tipoReceita", label: "Tipo", type: "status" },
        { key: "formaPagamento", label: "Pagamento" },
        { key: "dataRecebimento", label: "Data", type: "date" },
        { key: "ativa", label: "Ativa", type: "boolean" },
      ]}
      actions={[
        { label: "Inativar", endpoint: (item) => `/receitas/${item.id}/inativar`, danger: true },
        { label: "Reativar", endpoint: (item) => `/receitas/${item.id}/reativar` },
      ]}
    />
  );
}
