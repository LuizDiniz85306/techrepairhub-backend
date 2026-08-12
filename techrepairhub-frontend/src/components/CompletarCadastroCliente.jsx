import { useState } from "react";
import { ErrorMessage, FormCard, SuccessMessage } from "./ui";
import { normalizeError } from "../utils/formatters";

export default function CompletarCadastroCliente({ onCadastrar, aviso }) {
  const [form, setForm] = useState({
    cpf: "",
    telefone: "",
    whatsapp: "",
    endereco: "",
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  function handleChange(event) {
    const { name, value } = event.target;
    setForm((current) => ({ ...current, [name]: value }));
  }

  async function handleSubmit(event) {
    event.preventDefault();
    setLoading(true);
    setError("");
    setSuccess("");

    try {
      await onCadastrar(form);
      setSuccess("Cadastro de cliente concluído. Agora você pode cadastrar equipamentos.");
      setForm({ cpf: "", telefone: "", whatsapp: "", endereco: "" });
    } catch (err) {
      setError(normalizeError(err, "Não foi possível concluir seu cadastro de cliente."));
    } finally {
      setLoading(false);
    }
  }

  return (
    <FormCard title="Completar cadastro de cliente">
      <ErrorMessage message={error} />
      <SuccessMessage message={success} />
      <p className="muted">
        Seu usuário existe, mas ainda falta o registro de cliente para vincular equipamentos e ordens de serviço.
      </p>
      {aviso && <p className="notice">{aviso}</p>}
      <form onSubmit={handleSubmit}>
        <label>CPF</label>
        <input name="cpf" value={form.cpf} onChange={handleChange} required />
        <label>Telefone</label>
        <input name="telefone" value={form.telefone} onChange={handleChange} required />
        <label>WhatsApp</label>
        <input name="whatsapp" value={form.whatsapp} onChange={handleChange} required />
        <label>Endereço</label>
        <input name="endereco" value={form.endereco} onChange={handleChange} required />
        <button type="submit" disabled={loading}>
          {loading ? "Salvando..." : "Completar cadastro"}
        </button>
      </form>
    </FormCard>
  );
}
