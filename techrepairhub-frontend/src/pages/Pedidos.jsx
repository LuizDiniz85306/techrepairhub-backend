import ResourcePage from "../components/ResourcePage";

export default function Pedidos() {
  return (
    <ResourcePage
      title="Pedidos"
      description="Consulta de pedidos. O backend finaliza pedidos a partir do carrinho do cliente."
      listEndpoint="/pedidos"
      createEndpoint={null}
      noCreateMessage="Não existe PedidoCadastroDTO nem POST /pedidos direto. Use o carrinho e finalize por cliente no endpoint /pedidos/cliente/{clienteId}/finalizar."
      columns={[
        { key: "pedidoId", label: "ID" },
        { key: "nomeCliente", label: "Cliente" },
        { key: "status", label: "Status", type: "status" },
        { key: "valorTotal", label: "Valor total", type: "currency" },
        { key: "dataPedido", label: "Data", type: "date" },
      ]}
      actions={[
        { label: "Cancelar", endpoint: (item) => `/pedidos/${item.pedidoId}/cancelar`, danger: true },
        { label: "Entregar", endpoint: (item) => `/pedidos/${item.pedidoId}/entregar` },
      ]}
    />
  );
}
