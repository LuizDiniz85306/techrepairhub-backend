import { useEffect, useState } from "react";
import api from "../../api/axiosConfig";
import CompletarCadastroCliente from "../../components/CompletarCadastroCliente";
import {
  EmptyState,
  ErrorMessage,
  FormCard,
  Loading,
  PageHeader,
  SuccessMessage,
  TableCard,
} from "../../components/ui";
import useClienteLogado from "../../hooks/useClienteLogado";
import { formatDate, normalizeError } from "../../utils/formatters";

export default function MeusEquipamentos() {
  const { cliente, loadingCliente, clienteError, cadastrarClienteLogado } = useClienteLogado();
  const [equipamentos, setEquipamentos] = useState([]);
  const [form, setForm] = useState({
    tipo: "",
    marca: "",
    modelo: "",
    numeroSerie: "",
    descricao: "",
  });
  const [loading, setLoading] = useState(false);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  async function carregarEquipamentos() {
    setLoading(true);
    setError("");
    try {
      const response = await api.get("/equipamentos/meus");
      setEquipamentos(response.data);
    } catch (err) {
      setError(normalizeError(err, "Erro ao carregar seus equipamentos."));
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    if (cliente?.id) {
      carregarEquipamentos();
    }
  }, [cliente?.id]);

  function handleChange(event) {
    const { name, value } = event.target;
    setForm((current) => ({ ...current, [name]: value }));
  }

  async function cadastrarEquipamento(event) {
    event.preventDefault();
    if (!cliente?.id) return;

    setSaving(true);
    setError("");
    setSuccess("");
    try {
      await api.post("/equipamentos/meus", {
        tipo: form.tipo,
        marca: form.marca,
        modelo: form.modelo,
        numeroSerie: form.numeroSerie,
        descricao: form.descricao,
      });
      setSuccess("Equipamento cadastrado com sucesso.");
      setForm({ tipo: "", marca: "", modelo: "", numeroSerie: "", descricao: "" });
      await carregarEquipamentos();
    } catch (err) {
      setError(normalizeError(err, "Erro ao cadastrar equipamento."));
    } finally {
      setSaving(false);
    }
  }

  return (
    <div>
      <PageHeader
        title="Meus Equipamentos"
        description="Equipamentos vinculados ao seu cadastro de cliente."
      />
      {loadingCliente && <Loading text="Carregando dados do cliente..." />}
      <ErrorMessage message={cliente ? clienteError || error : error} />
      <SuccessMessage message={success} />

      {!loadingCliente && !cliente && (
        <CompletarCadastroCliente onCadastrar={cadastrarClienteLogado} aviso={clienteError} />
      )}

      {cliente && (
        <div className="content-grid">
          <FormCard title="Novo equipamento">
            <form onSubmit={cadastrarEquipamento}>
              <label>Tipo</label>
              <input name="tipo" value={form.tipo} onChange={handleChange} required />
              <label>Marca</label>
              <input name="marca" value={form.marca} onChange={handleChange} required />
              <label>Modelo</label>
              <input name="modelo" value={form.modelo} onChange={handleChange} required />
              <label>Número de série</label>
              <input name="numeroSerie" value={form.numeroSerie} onChange={handleChange} />
              <label>Descrição</label>
              <textarea name="descricao" value={form.descricao} onChange={handleChange} />
              <button type="submit" disabled={saving}>
                {saving ? "Salvando..." : "Cadastrar equipamento"}
              </button>
            </form>
          </FormCard>

          <TableCard title="Equipamentos vinculados">
            {loading ? <Loading /> : equipamentos.length === 0 ? <EmptyState /> : (
              <table>
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Tipo</th>
                    <th>Marca</th>
                    <th>Modelo</th>
                    <th>Série</th>
                    <th>Status</th>
                    <th>Cadastro</th>
                  </tr>
                </thead>
                <tbody>
                  {equipamentos.map((equipamento) => (
                    <tr key={equipamento.equipamentoId}>
                      <td>{equipamento.equipamentoId}</td>
                      <td>{equipamento.tipo}</td>
                      <td>{equipamento.marca}</td>
                      <td>{equipamento.modelo}</td>
                      <td>{equipamento.numeroSerie || "-"}</td>
                      <td>{equipamento.ativo === false ? "Inativo" : "Ativo"}</td>
                      <td>{formatDate(equipamento.dataCadastro)}</td>
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

