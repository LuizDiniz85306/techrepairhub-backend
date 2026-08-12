import { useState } from "react";
import api from "../api/axiosConfig";
import { EmptyState, ErrorMessage, FormCard, PageHeader, SuccessMessage, TableCard } from "../components/ui";
import { formatCurrency, normalizeError } from "../utils/formatters";

export default function Carrinho() {
  const [clienteId, setClienteId] = useState("");
  const [produtoId, setProdutoId] = useState("");
  const [quantidade, setQuantidade] = useState(1);
  const [carrinho, setCarrinho] = useState(null);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  async function carregar(event) {
    event?.preventDefault();
    setError("");
    try {
      const response = await api.get(`/carrinhos/cliente/${clienteId}`);
      setCarrinho(response.data);
    } catch (err) {
      setError(normalizeError(err, "Erro ao carregar carrinho."));
    }
  }

  async function adicionar(event) {
    event.preventDefault();
    setError("");
    setSuccess("");
    try {
      const response = await api.post(`/carrinhos/cliente/${clienteId}/itens`, {
        produtoId: Number(produtoId),
        quantidade: Number(quantidade),
      });
      setCarrinho(response.data);
      setSuccess("Item adicionado ao carrinho.");
    } catch (err) {
      setError(normalizeError(err, "Erro ao adicionar item."));
    }
  }

  async function finalizar() {
    setError("");
    setSuccess("");
    try {
      await api.post(`/pedidos/cliente/${clienteId}/finalizar`);
      setSuccess("Pedido finalizado a partir do carrinho.");
      await carregar();
    } catch (err) {
      setError(normalizeError(err, "Erro ao finalizar pedido."));
    }
  }

  return (
    <div>
      <PageHeader title="Carrinho" description="Consulta e operações do carrinho por cliente." />
      <ErrorMessage message={error} />
      <SuccessMessage message={success} />
      <div className="content-grid">
        <FormCard title="Buscar carrinho">
          <form onSubmit={carregar}>
            <label>Cliente ID</label>
            <input type="number" value={clienteId} onChange={(e) => setClienteId(e.target.value)} required />
            <button type="submit">Buscar</button>
          </form>
          <form onSubmit={adicionar}>
            <label>Produto ID</label>
            <input type="number" value={produtoId} onChange={(e) => setProdutoId(e.target.value)} required />
            <label>Quantidade</label>
            <input type="number" min="1" value={quantidade} onChange={(e) => setQuantidade(e.target.value)} required />
            <button type="submit" disabled={!clienteId}>Adicionar item</button>
          </form>
          <button type="button" className="btn-secondary full-button" onClick={finalizar} disabled={!clienteId}>Finalizar pedido</button>
        </FormCard>
        <TableCard title="Itens do carrinho">
          {!carrinho ? <EmptyState message="Informe um cliente para consultar." /> : (
            <table>
              <thead><tr><th>Produto</th><th>Quantidade</th><th>Valor</th><th>Subtotal</th></tr></thead>
              <tbody>
                {carrinho.itens?.map((item) => (
                  <tr key={item.itemId}>
                    <td>{item.nomeProduto}</td><td>{item.quantidade}</td><td>{formatCurrency(item.precoUnitario)}</td><td>{formatCurrency(item.subtotal)}</td>
                  </tr>
                ))}
                <tr><td colSpan="3"><strong>Total</strong></td><td><strong>{formatCurrency(carrinho.total)}</strong></td></tr>
              </tbody>
            </table>
          )}
        </TableCard>
      </div>
    </div>
  );
}
