import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../api/axiosConfig";
import { useAuth } from "../context/AuthContext";
import { normalizeError } from "../utils/formatters";

const initialForm = {
  nome: "",
  email: "",
  senha: "",
  cpf: "",
  telefone: "",
  whatsapp: "",
  endereco: "",
};

export default function CadastroCliente() {
  const navigate = useNavigate();
  const { login } = useAuth();
  const [form, setForm] = useState(initialForm);
  const [erro, setErro] = useState("");
  const [carregando, setCarregando] = useState(false);
  const [mostrarSenha, setMostrarSenha] = useState(false);

  function handleChange(event) {
    const { name, value } = event.target;
    setForm((current) => ({ ...current, [name]: value }));
  }

  async function handleSubmit(event) {
    event.preventDefault();
    setErro("");
    setCarregando(true);

    try {
      await api.post("/auth/cadastro-cliente", form);
      await login(form.email, form.senha);
      navigate("/cliente", { replace: true });
    } catch (error) {
      setErro(normalizeError(error, "Não foi possível criar sua conta."));
    } finally {
      setCarregando(false);
    }
  }

  return (
    <div className="login-container signup-container">
      <section className="login-shell signup-shell">
        <aside className="login-panel">
          <span className="login-brand-mark">TR</span>
          <h1>Crie sua conta de cliente</h1>
          <p>Entre no marketplace, acompanhe pedidos, cadastre equipamentos e abra ordens de serviço com seu próprio acesso.</p>
          <div className="login-panel-grid">
            <span>Compras no catálogo</span>
            <span>Meu carrinho</span>
            <span>Meus equipamentos</span>
            <span>Minhas OS</span>
          </div>
        </aside>

        <form className="login-card signup-card" onSubmit={handleSubmit}>
          <div className="login-card-header">
            <span>Cadastro</span>
            <h2>Nova conta</h2>
            <p>Preencha seus dados para acessar a área do cliente.</p>
          </div>

          {erro && <div className="alerta-erro">{erro}</div>}

          <div className="signup-grid">
            <label>
              Nome
              <input name="nome" value={form.nome} onChange={handleChange} placeholder="Seu nome" required />
            </label>

            <label>
              E-mail
              <input
                name="email"
                type="email"
                value={form.email}
                onChange={handleChange}
                placeholder="voce@email.com"
                autoComplete="email"
                required
              />
            </label>

            <label>
              CPF
              <input name="cpf" value={form.cpf} onChange={handleChange} placeholder="Somente números" required />
            </label>

            <label>
              Telefone
              <input name="telefone" value={form.telefone} onChange={handleChange} placeholder="(00) 00000-0000" required />
            </label>

            <label>
              WhatsApp
              <input name="whatsapp" value={form.whatsapp} onChange={handleChange} placeholder="(00) 00000-0000" required />
            </label>

            <label>
              Senha
              <span className="signup-password-row">
                <input
                  name="senha"
                  type={mostrarSenha ? "text" : "password"}
                  value={form.senha}
                  onChange={handleChange}
                  placeholder="Crie uma senha"
                  autoComplete="new-password"
                  required
                />
                <button type="button" onClick={() => setMostrarSenha((current) => !current)}>
                  {mostrarSenha ? "Ocultar" : "Mostrar"}
                </button>
              </span>
            </label>

            <label className="signup-full">
              Endereço
              <input name="endereco" value={form.endereco} onChange={handleChange} placeholder="Rua, número, bairro e cidade" required />
            </label>
          </div>

          <button className="login-submit" type="submit" disabled={carregando}>
            {carregando ? "Criando conta..." : "Criar conta"}
          </button>

          <div className="login-footer-links">
            <Link to="/login">Já tenho conta</Link>
            <Link to="/catalogo">Voltar ao catálogo</Link>
          </div>
        </form>
      </section>
    </div>
  );
}
