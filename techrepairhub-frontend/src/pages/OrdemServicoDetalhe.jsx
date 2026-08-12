import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import api from "../api/axiosConfig";
import { EmptyState, ErrorMessage, FormCard, Loading, PageHeader, SuccessMessage, TableCard } from "../components/ui";
import { formatCurrency, formatDate, normalizeError } from "../utils/formatters";
import { STATUS_ORDEM_SERVICO, TIPOS_HISTORICO_OS } from "../config/options";

export default function OrdemServicoDetalhe() {
  const { id } = useParams();
  const [ordem, setOrdem] = useState(null);
  const [historicos, setHistoricos] = useState([]);
  const [orcamentos, setOrcamentos] = useState([]);
  const [pecas, setPecas] = useState([]);
  const [relatorio, setRelatorio] = useState(null);
  const [historicoForm, setHistoricoForm] = useState({ tipo: "OBSERVACAO", descricao: "" });
  const [updateForm, setUpdateForm] = useState({ status: "", diagnostico: "", solucaoAplicada: "", valorTotal: "" });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  async function carregar() {
    setLoading(true);
    setError("");
    try {
      const [os, hist, orc, pecasUsadas, rel] = await Promise.all([
        api.get(`/ordens-servico/${id}`),
        api.get(`/historicos-ordem-servico/ordem-servico/${id}/desc`).catch(() => ({ data: [] })),
        api.get(`/orcamentos/ordem-servico/${id}`).catch(() => ({ data: [] })),
        api.get(`/pecas-utilizadas/ordem-servico/${id}`).catch(() => ({ data: [] })),
        api.get(`/relatorios-tecnicos/ordem-servico/${id}`).catch(() => ({ data: null })),
      ]);
      setOrdem(os.data);
      setHistoricos(hist.data);
      setOrcamentos(orc.data);
      setPecas(pecasUsadas.data);
      setRelatorio(rel.data);
      setUpdateForm({
        status: os.data.status || "",
        diagnostico: os.data.diagnostico || "",
        solucaoAplicada: os.data.solucaoAplicada || "",
        valorTotal: os.data.valorTotal || "",
      });
    } catch (err) {
      setError(normalizeError(err, "Erro ao carregar detalhe da OS."));
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    carregar();
  }, [id]);

  async function atualizar(event) {
    event.preventDefault();
    setError("");
    setSuccess("");
    try {
      await api.put(`/ordens-servico/${id}`, {
        tecnicoId: ordem?.tecnicoId || null,
        descricaoProblema: ordem?.descricaoProblema || "",
        diagnostico: updateForm.diagnostico,
        solucaoAplicada: updateForm.solucaoAplicada,
        status: updateForm.status,
        valorTotal: Number(updateForm.valorTotal || 0),
      });
      setSuccess("Ordem de serviço atualizada.");
      await carregar();
    } catch (err) {
      setError(normalizeError(err, "Erro ao atualizar OS."));
    }
  }

  async function registrarHistorico(event) {
    event.preventDefault();
    setError("");
    setSuccess("");
    try {
      await api.post("/historicos-ordem-servico", {
        ordemServicoId: Number(id),
        tipo: historicoForm.tipo,
        descricao: historicoForm.descricao,
      });
      setHistoricoForm({ tipo: "OBSERVACAO", descricao: "" });
      setSuccess("Histórico registrado.");
      await carregar();
    } catch (err) {
      setError(normalizeError(err, "Erro ao registrar histórico."));
    }
  }

  if (loading) return <Loading />;
  const osFechada = ["FINALIZADA", "CANCELADA"].includes(String(ordem?.status));

  return (
    <div>
      <PageHeader title={`OS #${id}`} description="Detalhes, histórico, orçamentos, peças e relatório técnico." />
      <ErrorMessage message={error} />
      <SuccessMessage message={success} />

      {ordem && (
        <section className="table-card detail-card">
          <h2>{ordem.nomeCliente} - {ordem.equipamento}</h2>
          <p><strong>Status:</strong> {ordem.status}</p>
          <p><strong>Problema:</strong> {ordem.descricaoProblema}</p>
          <p><strong>Técnico:</strong> {ordem.nomeTecnico || "-"}</p>
          <p><strong>Valor:</strong> {formatCurrency(ordem.valorTotal)}</p>
          <p><strong>Abertura:</strong> {formatDate(ordem.dataAbertura)}</p>
        </section>
      )}

      <div className="content-grid">
        {osFechada ? (
          <section className="form-card">
            <h2>OS encerrada</h2>
            <p className="muted">Esta ordem esta {String(ordem?.status).toLowerCase()} e fica disponivel apenas para consulta.</p>
          </section>
        ) : (
          <FormCard title="Atualizar OS">
            <form onSubmit={atualizar}>
              <label>Status</label>
              <select value={updateForm.status} onChange={(e) => setUpdateForm({ ...updateForm, status: e.target.value })}>
                {STATUS_ORDEM_SERVICO.map((status) => <option key={status} value={status}>{status}</option>)}
              </select>
              <label>Diagnóstico</label>
              <textarea value={updateForm.diagnostico} onChange={(e) => setUpdateForm({ ...updateForm, diagnostico: e.target.value })} />
              <label>Solução aplicada</label>
              <textarea value={updateForm.solucaoAplicada} onChange={(e) => setUpdateForm({ ...updateForm, solucaoAplicada: e.target.value })} />
              <label>Valor total</label>
              <input type="number" step="0.01" value={updateForm.valorTotal} onChange={(e) => setUpdateForm({ ...updateForm, valorTotal: e.target.value })} />
              <button type="submit">Atualizar</button>
            </form>
            <form onSubmit={registrarHistorico}>
              <label>Tipo de histórico</label>
              <select value={historicoForm.tipo} onChange={(e) => setHistoricoForm({ ...historicoForm, tipo: e.target.value })}>
                {TIPOS_HISTORICO_OS.map((tipo) => <option key={tipo} value={tipo}>{tipo}</option>)}
              </select>
              <label>Descrição</label>
              <textarea value={historicoForm.descricao} onChange={(e) => setHistoricoForm({ ...historicoForm, descricao: e.target.value })} required />
              <button type="submit">Registrar histórico</button>
            </form>
          </FormCard>
        )}

        <TableCard title="Histórico">
          {historicos.length === 0 ? <EmptyState /> : (
            <table><thead><tr><th>Data</th><th>Tipo</th><th>Descrição</th><th>Status</th></tr></thead>
              <tbody>{historicos.map((h) => <tr key={h.historicoId}><td>{formatDate(h.dataRegistro)}</td><td>{h.tipo}</td><td>{h.descricao}</td><td>{h.statusNovo || h.statusAnterior || "-"}</td></tr>)}</tbody>
            </table>
          )}
        </TableCard>
      </div>

      <div className="three-columns">
        <TableCard title="Orçamentos">
          {orcamentos.length === 0 ? <EmptyState /> : <table><tbody>{orcamentos.map((o) => <tr key={o.id}><td>{o.status}</td><td>{formatCurrency(o.valorTotal)}</td></tr>)}</tbody></table>}
        </TableCard>
        <TableCard title="Peças utilizadas">
          {pecas.length === 0 ? <EmptyState /> : <table><tbody>{pecas.map((p) => <tr key={p.id}><td>{p.peca}</td><td>{p.quantidade}</td><td>{formatCurrency(p.subtotal)}</td></tr>)}</tbody></table>}
        </TableCard>
        <TableCard title="Relatório técnico">
          {!relatorio ? <EmptyState /> : <p>{relatorio.diagnostico}</p>}
        </TableCard>
      </div>
    </div>
  );
}
