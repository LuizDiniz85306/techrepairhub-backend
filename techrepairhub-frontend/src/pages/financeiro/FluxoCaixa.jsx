import { useEffect, useState } from "react";
import api from "../../api/axiosConfig";
import { ErrorMessage, Loading, PageHeader, StatusBadge } from "../../components/ui";
import { formatCurrency, normalizeError, toDateTimeLocal } from "../../utils/formatters";

export default function FluxoCaixa() {
  const [inicio, setInicio] = useState(toDateTimeLocal(new Date(new Date().getFullYear(), new Date().getMonth(), 1)));
  const [fim, setFim] = useState(toDateTimeLocal(new Date()));
  const [dados, setDados] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  async function carregar() {
    setLoading(true);
    setError("");
    try {
      const response = await api.get(`/fluxo-caixa?inicio=${inicio}:00&fim=${fim}:00`);
      setDados(response.data);
    } catch (err) {
      setError(normalizeError(err, "Erro ao carregar fluxo de caixa."));
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    carregar();
  }, []);

  return (
    <div>
      <PageHeader title="Fluxo de Caixa" description="Resumo financeiro por período." />
      <ErrorMessage message={error} />
      <div className="filter-bar">
        <label>Início<input type="datetime-local" value={inicio} onChange={(e) => setInicio(e.target.value)} /></label>
        <label>Fim<input type="datetime-local" value={fim} onChange={(e) => setFim(e.target.value)} /></label>
        <button type="button" onClick={carregar}>Filtrar</button>
      </div>
      {loading || !dados ? <Loading /> : (
        <div className="cards-grid">
          <div className="card"><span>Receitas</span><strong>{formatCurrency(dados.totalReceitas)}</strong></div>
          <div className="card"><span>Despesas</span><strong>{formatCurrency(dados.totalDespesas)}</strong></div>
          <div className="card"><span>Saldo</span><strong>{formatCurrency(dados.saldoFinal)}</strong></div>
          <div className="card"><span>Resultado</span><strong><StatusBadge value={dados.resultado} /></strong></div>
          <div className="card"><span>Qtd. receitas</span><strong>{dados.quantidadeReceitas}</strong></div>
          <div className="card"><span>Qtd. despesas</span><strong>{dados.quantidadeDespesas}</strong></div>
        </div>
      )}
    </div>
  );
}
