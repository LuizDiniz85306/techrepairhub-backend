import { useEffect, useState } from "react";
import api from "../api/axiosConfig";

export default function Dashboard() {
  const [dashboard, setDashboard] = useState(null);
  const [erro, setErro] = useState("");

  useEffect(() => {
    carregarDashboard();
  }, []);

  async function carregarDashboard() {
    try {
      const response = await api.get(
        "/dashboard?inicio=2026-06-01T00:00:00&fim=2026-06-30T23:59:59"
      );

      setDashboard(response.data);
    } catch (error) {
      setErro("Erro ao carregar dashboard.");
    }
  }

  if (erro) {
    return <div className="alerta-erro">{erro}</div>;
  }

  if (!dashboard) {
    return <p>Carregando dashboard...</p>;
  }

  return (
    <div>
      <h1>Dashboard Administrativo</h1>

      <div className="cards-grid">
        <div className="card">
          <span>Clientes</span>
          <strong>{dashboard.totalClientes}</strong>
        </div>

        <div className="card">
          <span>Técnicos</span>
          <strong>{dashboard.totalTecnicos}</strong>
        </div>

        <div className="card">
          <span>Produtos</span>
          <strong>{dashboard.totalProdutos}</strong>
        </div>

        <div className="card">
          <span>Pedidos</span>
          <strong>{dashboard.totalPedidos}</strong>
        </div>

        <div className="card">
          <span>Ordens de Serviço</span>
          <strong>{dashboard.totalOrdensServico}</strong>
        </div>

        <div className="card">
          <span>OS abertas</span>
          <strong>{dashboard.osAbertas}</strong>
        </div>

        <div className="card">
          <span>OS em execução</span>
          <strong>{dashboard.osEmExecucao}</strong>
        </div>

        <div className="card">
          <span>OS finalizadas</span>
          <strong>{dashboard.osFinalizadas}</strong>
        </div>

        <div className="card">
          <span>Receitas</span>
          <strong>R$ {dashboard.totalReceitas}</strong>
        </div>

        <div className="card">
          <span>Despesas</span>
          <strong>R$ {dashboard.totalDespesas}</strong>
        </div>

        <div className="card">
          <span>Saldo</span>
          <strong>R$ {dashboard.saldoFinanceiro}</strong>
        </div>

        <div className="card">
          <span>Resultado</span>
          <strong>{dashboard.resultadoFinanceiro}</strong>
        </div>
      </div>
    </div>
  );
}