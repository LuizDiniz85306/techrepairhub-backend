import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import api from "../../api/axiosConfig";
import CompletarCadastroCliente from "../../components/CompletarCadastroCliente";
import {
  EmptyState,
  ErrorMessage,
  FormCard,
  Loading,
  PageHeader,
  StatusBadge,
  SuccessMessage,
  TableCard,
} from "../../components/ui";
import useClienteLogado from "../../hooks/useClienteLogado";
import { formatCurrency, formatDate, normalizeError } from "../../utils/formatters";

export default function MinhasOrdensServico() {
  const { cliente, loadingCliente, clienteError, cadastrarClienteLogado } = useClienteLogado();
  const [ordens, setOrdens] = useState([]);
  const [equipamentos, setEquipamentos] = useState([]);
  const [form, setForm] = useState({ equipamentoId: "", descricaoProblema: "" });
  const [loading, setLoading] = useState(false);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  async function carregarDados() {
    setLoading(true);
    setError("");
    try {
      const [ordensResponse, equipamentosResponse] = await Promise.all([
        api.get("/ordens-servico/minhas"),
        api.get("/equipamentos/meus"),
      ]);
      setOrdens(ordensResponse.data);
      setEquipamentos(equipamentosResponse.data);
    } catch (err) {
      setError(normalizeError(err, "Erro ao carregar suas ordens de serviço."));
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    if (cliente?.id) {
      carregarDados();
    }
  }, [cliente?.id]);

  function handleChange(event) {
    const { name, value } = event.target;
    setForm((current) => ({ ...current, [name]: value }));
  }

  async function abrirOrdem(event) {
    event.preventDefault();
    if (!cliente?.id) return;

    setSaving(true);
    setError("");
    setSuccess("");
    try {
      await api.post("/ordens-servico/minhas", {
        equipamentoId: Number(form.equipamentoId),
        descricaoProblema: form.descricaoProblema,
      });
      setSuccess("Ordem de serviço aberta com sucesso.");
      setForm({ equipamentoId: "", descricaoProblema: "" });
      await carregarDados();
    } catch (err) {
      setError(normalizeError(err, "Erro ao abrir ordem de serviço."));
    } finally {
      setSaving(false);
    }
  }

  return (
    <div>
      <PageHeader
        title="Minhas Ordens de Serviço"
        description="Ordens vinculadas ao seu cadastro de cliente."
      />
      {loadingCliente && <Loading text="Carregando dados do cliente..." />}
      <ErrorMessage message={cliente ? clienteError || error : error} />
      <SuccessMessage message={success} />

      {!loadingCliente && !cliente && (
        <CompletarCadastroCliente onCadastrar={cadastrarClienteLogado} aviso={clienteError} />
      )}

      {cliente && (
        <div className="content-grid">
          <FormCard title="Abrir ordem de serviço">
            <form onSubmit={abrirOrdem}>
              <label>Equipamento</label>
              <select
                name="equipamentoId"
                value={form.equipamentoId}
                onChange={handleChange}
                required
              >
                <option value="">Selecione</option>
                {equipamentos.map((equipamento) => (
                  <option key={equipamento.equipamentoId} value={equipamento.equipamentoId}>
                    {equipamento.tipo} {equipamento.marca} {equipamento.modelo}
                  </option>
                ))}
              </select>
              <label>Descrição do problema</label>
              <textarea
                name="descricaoProblema"
                value={form.descricaoProblema}
                onChange={handleChange}
                required
              />
              <button type="submit" disabled={saving || equipamentos.length === 0}>
                {saving ? "Salvando..." : "Abrir ordem"}
              </button>
              {equipamentos.length === 0 && (
                <div className="notice">
                  Cadastre um equipamento antes de abrir uma ordem de serviço.
                </div>
              )}
            </form>
          </FormCard>

          <TableCard title="Ordens vinculadas">
            {loading ? <Loading /> : ordens.length === 0 ? <EmptyState /> : (
              <table>
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Equipamento</th>
                    <th>Status</th>
                    <th>Problema</th>
                    <th>Valor</th>
                    <th>Abertura</th>
                    <th>Detalhe</th>
                  </tr>
                </thead>
                <tbody>
                  {ordens.map((ordem) => (
                    <tr key={ordem.ordemServicoId}>
                      <td>{ordem.ordemServicoId}</td>
                      <td>{ordem.equipamento}</td>
                      <td><StatusBadge value={ordem.status} /></td>
                      <td>{ordem.descricaoProblema}</td>
                      <td>{formatCurrency(ordem.valorTotal)}</td>
                      <td>{formatDate(ordem.dataAbertura)}</td>
                      <td><Link to={`/ordens-servico/${ordem.ordemServicoId}`}>Abrir</Link></td>
                    </tr>
                  ))}
                </tbody>
              </table>
            )}
          </TableCard>
        </div>
      )}
    </div>
  );
}

