import ResourcePage from "../../components/ResourcePage";
import { FORMAS_PAGAMENTO, TIPOS_DESPESA } from "../../config/options";

export default function Despesas() {
  return (
    <ResourcePage
      title="Despesas"
      description="Lançamento e consulta de despesas."
      listEndpoint="/despesas"
      createEndpoint="/despesas"
      fields={[
        { name: "descricao", label: "Descrição" },
        { name: "valor", label: "Valor", type: "number", step: "0.01", min: "0.01" },
        { name: "tipoDespesa", label: "Tipo", type: "select", options: TIPOS_DESPESA },
        { name: "formaPagamento", label: "Forma de pagamento", type: "select", options: FORMAS_PAGAMENTO },
        { name: "paga", label: "Despesa paga", type: "checkbox", defaultValue: true, required: false },
        { name: "observacao", label: "Observação", type: "textarea", optional: true },
      ]}
      columns={[
        { key: "id", label: "ID" },
        { key: "descricao", label: "Descrição" },
        { key: "valor", label: "Valor", type: "currency" },
        { key: "tipoDespesa", label: "Tipo", type: "status" },
        { key: "formaPagamento", label: "Pagamento" },
        { key: "paga", label: "Paga", type: "boolean" },
        { key: "ativa", label: "Ativa", type: "boolean" },
      ]}
      actions={[
        { label: "Paga", endpoint: (item) => `/despesas/${item.id}/marcar-paga` },
        { label: "Pendente", endpoint: (item) => `/despesas/${item.id}/marcar-pendente` },
        { label: "Inativar", endpoint: (item) => `/despesas/${item.id}/inativar`, danger: true },
        { label: "Reativar", endpoint: (item) => `/despesas/${item.id}/reativar` },
      ]}
    />
  );
}
