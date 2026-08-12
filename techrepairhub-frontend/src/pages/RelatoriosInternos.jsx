import { useState } from "react";
import api from "../api/axiosConfig";
import { EmptyState, ErrorMessage, Loading, PageHeader } from "../components/ui";
import { normalizeError } from "../utils/formatters";

const RELATORIOS = [
  ["produtos-categoria-estoque", "Produtos, categorias e estoque"],
  ["pedidos-clientes", "Pedidos e clientes"],
  ["ordens-servico", "Ordens de serviço"],
  ["produtos-estoque-abaixo-media", "Produtos abaixo da média"],
  ["pedidos-acima-media", "Pedidos acima da média"],
  ["clientes-com-pedidos", "Clientes com pedidos"],
  ["total-vendido-cliente", "Total vendido por cliente"],
  ["ordens-por-tecnico", "Ordens por técnico"],
];

export default function RelatoriosInternos() {
  const [ativo, setAtivo] = useState(RELATORIOS[0][0]);
  const [dados, setDados] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  async function carregar(slug = ativo) {
    setAtivo(slug);
    setLoading(true);
    setError("");
    try {
      const response = await api.get(`/relatorios/${slug}`);
      setDados(response.data);
    } catch (err) {
      setError(normalizeError(err, "Erro ao carregar relatório."));
    } finally {
      setLoading(false);
    }
  }

  const keys = dados[0] ? Object.keys(dados[0]) : [];

  return (
    <div>
      <PageHeader title="Relatórios Internos" description="Relatórios gerenciais existentes no back-end." />
      <ErrorMessage message={error} />
      <div className="tabs">
        {RELATORIOS.map(([slug, label]) => (
          <button key={slug} type="button" className={ativo === slug ? "active" : ""} onClick={() => carregar(slug)}>
            {label}
          </button>
        ))}
      </div>
      {loading ? <Loading /> : dados.length === 0 ? <EmptyState message="Escolha um relatório para carregar." /> : (
        <section className="table-card">
          <table>
            <thead><tr>{keys.map((key) => <th key={key}>{key}</th>)}</tr></thead>
            <tbody>{dados.map((row, index) => <tr key={index}>{keys.map((key) => <td key={key}>{String(row[key] ?? "-")}</td>)}</tr>)}</tbody>
          </table>
        </section>
      )}
    </div>
  );
}
