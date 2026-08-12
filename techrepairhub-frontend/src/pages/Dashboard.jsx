import { useEffect, useState } from "react";
import api from "../api/axiosConfig";
import { ErrorMessage, Loading, PageHeader, StatusBadge } from "../components/ui";
import { useAuth } from "../context/AuthContext";
import { formatCurrency, normalizeError, toDateTimeLocal } from "../utils/formatters";

function MetricCard({ label, value, tone = "default" }) {
  return (
    <div className={`card metric-card metric-${tone}`}>
      <span>{label}</span>
      <strong>{value ?? 0}</strong>
    </div>
  );
}

export default function Dashboard() {
  const { nome } = useAuth();
  const [dashboard, setDashboard] = useState(null);
  const [inicio, setInicio] = useState(toDateTimeLocal(new Date(new Date().getFullYear(), new Date().getMonth(), 1)));
  const [fim, setFim] = useState(toDateTimeLocal(new Date()));
  const [erro, setErro] = useState("");
  const [loading, setLoading] = useState(false);

  async function carregarDashboard() {
    setErro("");
    setLoading(true);
    try {
      const response = await api.get(`/dashboard?inicio=${inicio}:00&fim=${fim}:00`);
      setDashboard(response.data);
    } catch (error) {
      setErro(normalizeError(error, "Erro ao carregar dashboard."));
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    carregarDashboard();
  }, []);

  return (
    <div className="page dashboard-page">
      <PageHeader title="Dashboard Administrativo" description="Indicadores gerais do período selecionado." />
      <ErrorMessage message={erro} />

      <section className="dashboard-hero">
        <div>
          <span className="eyebrow">Visão geral</span>
          <h2>Olá, {nome || "usuário"}</h2>
          <p>Acompanhe clientes, ordens de serviço, estoque e resultado financeiro em um painel único.</p>
        </div>
        <div className="dashboard-filter">
          <label>Início<input type="datetime-local" value={inicio} onChange={(e) => setInicio(e.target.value)} /></label>
          <label>Fim<input type="datetime-local" value={fim} onChange={(e) => setFim(e.target.value)} /></label>
          <button type="button" className="btn btn-primary" onClick={carregarDashboard}>Filtrar</button>
        </div>
      </section>

      {loading || !dashboard ? (
        <Loading text="Carregando dashboard..." />
      ) : (
        <>
          <section className="dashboard-section">
            <h2>Operação</h2>
            <div className="cards-grid">
              <MetricCard label="Clientes" value={dashboard.totalClientes} />
              <MetricCard label="Técnicos" value={dashboard.totalTecnicos} />
              <MetricCard label="Produtos" value={dashboard.totalProdutos} />
              <MetricCard label="Pedidos" value={dashboard.totalPedidos} />
            </div>
          </section>

          <section className="dashboard-section">
            <h2>Ordens de Serviço</h2>
            <div className="cards-grid">
              <MetricCard label="Total de OS" value={dashboard.totalOrdensServico} tone="info" />
              <MetricCard label="Abertas" value={dashboard.osAbertas} tone="warning" />
              <MetricCard label="Em análise" value={dashboard.osEmAnalise} tone="warning" />
              <MetricCard label="Aguardando orçamento" value={dashboard.osAguardandoOrcamento} tone="warning" />
              <MetricCard label="Aguardando aprovação" value={dashboard.osAguardandoAprovacao} tone="warning" />
              <MetricCard label="Em execução" value={dashboard.osEmExecucao} tone="info" />
              <MetricCard label="Finalizadas" value={dashboard.osFinalizadas} tone="success" />
              <MetricCard label="Canceladas" value={dashboard.osCanceladas} tone="danger" />
            </div>
          </section>

          <section className="dashboard-section">
            <h2>Financeiro e Estoque</h2>
            <div className="cards-grid">
              <MetricCard label="Peças com estoque baixo" value={dashboard.pecasComEstoqueBaixo} tone="danger" />
              <MetricCard label="Receitas" value={formatCurrency(dashboard.totalReceitas)} tone="success" />
              <MetricCard label="Despesas" value={formatCurrency(dashboard.totalDespesas)} tone="danger" />
              <MetricCard label="Saldo" value={formatCurrency(dashboard.saldoFinanceiro)} tone="info" />
              <div className="card metric-card result-card">
                <span>Resultado financeiro</span>
                <strong><StatusBadge value={dashboard.resultadoFinanceiro} /></strong>
              </div>
            </div>
          </section>
        </>
      )}
    </div>
  );
}
