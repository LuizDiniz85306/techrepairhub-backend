import { useEffect, useState } from "react";
import api from "../api/axiosConfig";
import { ErrorMessage, FormCard, PageHeader, SuccessMessage, TableCard, Loading, EmptyState } from "../components/ui";
import { normalizeError } from "../utils/formatters";

function nomeTecnico(tecnico) {
  return tecnico.nome || tecnico.usuario?.nome || tecnico.usuarioNome || "-";
}

function emailTecnico(tecnico) {
  return tecnico.email || tecnico.usuario?.email || tecnico.usuarioEmail || "-";
}

export default function Tecnicos() {
  const [tecnicos, setTecnicos] = useState([]);
  const [form, setForm] = useState({ nome: "", email: "", senha: "", especialidade: "" });
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  async function carregar() {
    setLoading(true);
    try {
      const response = await api.get("/tecnicos");
      setTecnicos(response.data);
    } catch (err) {
      setError(normalizeError(err, "Erro ao carregar técnicos."));
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    carregar();
  }, []);

  function handleChange(event) {
    const { name, value } = event.target;
    setForm((current) => ({ ...current, [name]: value }));
  }

  async function cadastrar(event) {
    event.preventDefault();
    setSaving(true);
    setError("");
    setSuccess("");
    try {
      const usuarioResponse = await api.post("/usuarios", {
        nome: form.nome,
        email: form.email,
        senha: form.senha,
        perfil: "TECNICO",
      });
      const usuarioId = usuarioResponse.data.id || usuarioResponse.data.usuarioId;
      if (!usuarioId) throw new Error("O back-end não retornou o ID do usuário criado.");
      await api.post("/tecnicos", { usuarioId, especialidade: form.especialidade });
      setSuccess("Técnico cadastrado com sucesso.");
      setForm({ nome: "", email: "", senha: "", especialidade: "" });
      await carregar();
    } catch (err) {
      setError(normalizeError(err, "Erro ao cadastrar técnico."));
    } finally {
      setSaving(false);
    }
  }

  return (
    <div>
      <PageHeader title="Técnicos" description="Cadastro e consulta de técnicos." />
      <ErrorMessage message={error} />
      <SuccessMessage message={success} />
      <div className="content-grid">
        <FormCard title="Novo técnico">
          <form onSubmit={cadastrar}>
            <label>Nome</label>
            <input name="nome" value={form.nome} onChange={handleChange} required />
            <label>E-mail</label>
            <input type="email" name="email" value={form.email} onChange={handleChange} required />
            <label>Senha</label>
            <input type="password" name="senha" value={form.senha} onChange={handleChange} required />
            <label>Especialidade</label>
            <input name="especialidade" value={form.especialidade} onChange={handleChange} required />
            <button type="submit" disabled={saving}>{saving ? "Salvando..." : "Cadastrar técnico"}</button>
          </form>
        </FormCard>
        <TableCard title="Técnicos cadastrados">
          {loading ? <Loading /> : tecnicos.length === 0 ? <EmptyState /> : (
            <table>
              <thead><tr><th>ID</th><th>Usuário ID</th><th>Nome</th><th>E-mail</th><th>Especialidade</th><th>Status</th></tr></thead>
              <tbody>
                {tecnicos.map((tecnico) => (
                  <tr key={tecnico.id}>
                    <td>{tecnico.id}</td><td>{tecnico.usuarioId || tecnico.usuario?.id || "-"}</td>
                    <td>{nomeTecnico(tecnico)}</td><td>{emailTecnico(tecnico)}</td>
                    <td>{tecnico.especialidade || "-"}</td><td>{tecnico.ativo === false ? "Inativo" : "Ativo"}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </TableCard>
      </div>
    </div>
  );
}

