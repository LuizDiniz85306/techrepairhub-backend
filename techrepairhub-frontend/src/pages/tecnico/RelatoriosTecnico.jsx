import { useEffect, useMemo, useState } from "react";
import api from "../../api/axiosConfig";
import {
  EmptyState,
  ErrorMessage,
  FormCard,
  Loading,
  PageHeader,
  SuccessMessage,
  TableCard,
} from "../../components/ui";
import useTecnicoLogado from "../../hooks/useTecnicoLogado";
import { formatDate, normalizeError } from "../../utils/formatters";

const initialForm = {
  ordemServicoId: "",
  problemaRelatado: "",
  diagnostico: "",
  procedimentosExecutados: "",
  testesEfetuados: "",
  resultadoObtido: "",
  observacoesAdicionais: "",
};

export default function RelatoriosTecnico() {
  const { tecnico, loadingTecnico, tecnicoError, salvarTecnicoManual } = useTecnicoLogado();
  const [tecnicoIdManual, setTecnicoIdManual] = useState("");
  const [ordens, setOrdens] = useState([]);
  const [relatorios, setRelatorios] = useState([]);
  const [form, setForm] = useState(initialForm);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  async function carregarOrdens() {
    setLoading(true);
    setError("");
    try {
      const response = tecnico?.id ? await api.get("/ordens-servico/minhas") : { data: [] };
      setOrdens(response.data);
    } catch (err) {
      setOrdens([]);
      setError(normalizeError(err, "Erro ao carregar ordens para relatório."));
    } finally {
      setLoading(false);
    }
  }

  async function carregarRelatorios(tecnicoId) {
    if (!tecnicoId) {
      setRelatorios([]);
      return;
    }

    try {
      const response = await api.get(`/relatorios-tecnicos/tecnico/${tecnicoId}`);
      setRelatorios(response.data);
    } catch (err) {
      setRelatorios([]);
      setError(normalizeError(err, "Erro ao carregar relatórios do técnico."));
    }
  }

  useEffect(() => {
    if (!loadingTecnico) carregarOrdens();
  }, [loadingTecnico, tecnico?.id]);

  useEffect(() => {
    if (tecnico?.id) carregarRelatorios(tecnico.id);
  }, [tecnico?.id]);

  const ordensDoTecnico = useMemo(() => ordens, [ordens]);

  function handleChange(event) {
    const { name, value } = event.target;
    setForm((current) => ({ ...current, [name]: value }));
  }

  async function handleSubmit(event) {
    event.preventDefault();
    if (!tecnico?.id) {
      setError("Informe seu ID de técnico antes de criar relatório.");
      return;
    }

    setSaving(true);
    setError("");
    setSuccess("");
    try {
      await api.post("/relatorios-tecnicos", {
        ...form,
        ordemServicoId: Number(form.ordemServicoId),
        tecnicoId: Number(tecnico.id),
      });
      setForm(initialForm);
      setSuccess("Relatório técnico criado com sucesso.");
      await carregarRelatorios(tecnico.id);
    } catch (err) {
      setError(normalizeError(err, "Erro ao criar relatório técnico."));
    } finally {
      setSaving(false);
    }
  }

  function salvarManual(event) {
    event.preventDefault();
    salvarTecnicoManual(tecnicoIdManual);
    setTecnicoIdManual("");
  }

  return (
    <div>
      <PageHeader title="Relatórios do Técnico" description="Criação e consulta dos relatórios das OS atribuídas." />
      <ErrorMessage message={error || tecnicoError} />
      <SuccessMessage message={success} />

      {!loadingTecnico && !tecnico?.id && (
        <FormCard title="Identificar técnico">
          <form onSubmit={salvarManual}>
            <label>ID do técnico</label>
            <input
              type="number"
              value={tecnicoIdManual}
              onChange={(event) => setTecnicoIdManual(event.target.value)}
              placeholder="Ex.: 1"
              required
            />
            <button type="submit">Usar este técnico</button>
          </form>
        </FormCard>
      )}

      {loading || loadingTecnico ? (
        <Loading />
      ) : (
        <div className="content-grid">
          <FormCard title="Novo relatório">
            <form onSubmit={handleSubmit}>
              <label>Ordem de serviço</label>
              <select name="ordemServicoId" value={form.ordemServicoId} onChange={handleChange} required>
                <option value="">Selecione</option>
                {ordensDoTecnico.map((ordem) => (
                  <option key={ordem.ordemServicoId} value={ordem.ordemServicoId}>
                    OS #{ordem.ordemServicoId} - {ordem.nomeCliente} - {ordem.equipamento}
                  </option>
                ))}
              </select>

              <label>Problema relatado</label>
              <textarea name="problemaRelatado" value={form.problemaRelatado} onChange={handleChange} required />
              <label>Diagnóstico</label>
              <textarea name="diagnostico" value={form.diagnostico} onChange={handleChange} required />
              <label>Procedimentos executados</label>
              <textarea name="procedimentosExecutados" value={form.procedimentosExecutados} onChange={handleChange} required />
              <label>Testes efetuados</label>
              <textarea name="testesEfetuados" value={form.testesEfetuados} onChange={handleChange} />
              <label>Resultado obtido</label>
              <textarea name="resultadoObtido" value={form.resultadoObtido} onChange={handleChange} />
              <label>Observações adicionais</label>
              <textarea name="observacoesAdicionais" value={form.observacoesAdicionais} onChange={handleChange} />

              <button type="submit" disabled={saving || !tecnico?.id || ordensDoTecnico.length === 0}>
                {saving ? "Salvando..." : "Criar relatório"}
              </button>
            </form>
          </FormCard>

          <TableCard title="Meus relatórios">
            {relatorios.length === 0 ? (
              <EmptyState message="Nenhum relatório técnico encontrado." />
            ) : (
              <table>
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>OS</th>
                    <th>Cliente</th>
                    <th>Equipamento</th>
                    <th>Diagnóstico</th>
                    <th>Data</th>
                  </tr>
                </thead>
                <tbody>
                  {relatorios.map((relatorio) => (
                    <tr key={relatorio.id}>
                      <td>{relatorio.id}</td>
                      <td>{relatorio.ordemServicoId}</td>
                      <td>{relatorio.cliente || "-"}</td>
                      <td>{relatorio.equipamento || "-"}</td>
                      <td>{relatorio.diagnostico || "-"}</td>
                      <td>{formatDate(relatorio.dataRelatorio)}</td>
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
