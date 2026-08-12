import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import api from "../../api/axiosConfig";
import { EmptyState, ErrorMessage, Loading, PageHeader, SuccessMessage, TableCard } from "../../components/ui";
import { formatCurrency, normalizeError } from "../../utils/formatters";

export default function MeuCarrinho() {
  const [carrinho, setCarrinho] = useState(null);
  const [loading, setLoading] = useState(true);
  const [finalizando, setFinalizando] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  useEffect(() => {
    carregar();
  }, []);

  async function carregar() {
    setError("");
    try {
      const response = await api.get("/carrinhos/meu");
      setCarrinho(response.data);
    } catch (err) {
      setError(normalizeError(err, "Erro ao carregar carrinho."));
    } finally {
      setLoading(false);
    }
  }

  async function removerItem(itemId) {
    setError("");
    setSuccess("");
    try {
      const response = await api.delete(`/carrinhos/itens/${itemId}`);
      setCarrinho(response.data);
      setSuccess("Item removido do carrinho.");
    } catch (err) {
      setError(normalizeError(err, "Erro ao remover item."));
    }
  }

  async function limparCarrinho() {
    setError("");
    setSuccess("");
    try {
      const response = await api.delete("/carrinhos/meu/limpar");
      setCarrinho(response.data);
      setSuccess("Carrinho limpo.");
    } catch (err) {
      setError(normalizeError(err, "Erro ao limpar carrinho."));
    }
  }

  async function finalizarPedido() {
    setError("");
    setSuccess("");
    setFinalizando(true);
    try {
      const response = await api.post("/pedidos/meus/finalizar");
      setSuccess(`Pedido #${response.data.pedidoId || response.data.id || ""} finalizado com sucesso.`);
      await carregar();
    } catch (err) {
      setError(normalizeError(err, "Erro ao finalizar pedido."));
    } finally {
      setFinalizando(false);
    }
  }

  const itens = carrinho?.itens || [];
  const carrinhoVazio = itens.length === 0;

  return (
    <div>
      <PageHeader title="Meu carrinho" description="Revise os produtos antes de finalizar o pedido." />
      <ErrorMessage message={error} />
      <SuccessMessage message={success} />

      {loading ? <Loading /> : (
        <div className="content-grid">
          <TableCard title="Produtos no carrinho">
            {carrinhoVazio ? <EmptyState message="Seu carrinho está vazio." /> : (
              <table>
                <thead>
                  <tr>
                    <th>Produto</th>
                    <th>Quantidade</th>
                    <th>Valor</th>
                    <th>Subtotal</th>
                    <th>Ações</th>
                  </tr>
                </thead>
                <tbody>
                  {itens.map((item) => (
                    <tr key={item.itemId}>
                      <td>{item.nomeProduto}</td>
                      <td>{item.quantidade}</td>
                      <td>{formatCurrency(item.precoUnitario)}</td>
                      <td>{formatCurrency(item.subtotal)}</td>
                      <td>
                        <button type="button" className="btn-danger" onClick={() => removerItem(item.itemId)}>
                          Remover
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            )}
          </TableCard>

          <section className="form-card cart-summary-panel">
            <h2>Resumo</h2>
            <p>Total</p>
            <strong>{formatCurrency(carrinho?.total || 0)}</strong>
            <button
              type="button"
              className="market-buy-button full-button"
              onClick={finalizarPedido}
              disabled={carrinhoVazio || finalizando}
            >
              {finalizando ? "Finalizando..." : "Finalizar pedido"}
            </button>
            <button
              type="button"
              className="market-outline-button full-button"
              onClick={limparCarrinho}
              disabled={carrinhoVazio || finalizando}
            >
              Limpar carrinho
            </button>
            <Link to="/catalogo" className="market-outline-button full-button">Continuar comprando</Link>
          </section>
        </div>
      )}
    </div>
  );
}
