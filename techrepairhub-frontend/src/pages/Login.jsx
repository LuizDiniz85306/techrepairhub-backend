import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import { getHomeRouteByPerfil } from "../utils/authRoutes";

export default function Login() {
  const navigate = useNavigate();
  const { login } = useAuth();

  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [erro, setErro] = useState("");
  const [carregando, setCarregando] = useState(false);
  const [mostrarSenha, setMostrarSenha] = useState(false);

  async function handleSubmit(event) {
    event.preventDefault();

    setErro("");
    setCarregando(true);

    try {
      const data = await login(email, senha);
      navigate(getHomeRouteByPerfil(data.perfil), { replace: true });
    } catch (error) {
      setErro("E-mail ou senha inválidos.");
    } finally {
      setCarregando(false);
    }
  }

  return (
    <div className="login-container">
      <section className="login-shell">
        <aside className="login-panel">
          <span className="login-brand-mark">TR</span>
          <h1>TechRepair Hub</h1>
          <p>Área segura para clientes, técnicos e equipe acompanharem serviços, produtos e atendimento.</p>
          <div className="login-panel-grid">
            <span>Ordens de serviço</span>
            <span>Catálogo público</span>
            <span>Área do cliente</span>
            <span>Gestão técnica</span>
          </div>
        </aside>

        <form className="login-card" onSubmit={handleSubmit}>
          <div className="login-card-header">
            <span>Acesso</span>
            <h2>Entrar na conta</h2>
            <p>Use seu e-mail e senha cadastrados no sistema.</p>
          </div>

          {erro && <div className="alerta-erro">{erro}</div>}

          <label>E-mail</label>
          <input
            type="email"
            placeholder="Digite seu e-mail"
            value={email}
            onChange={(event) => setEmail(event.target.value)}
            autoComplete="email"
            required
          />

          <div className="login-password-label">
            <label>Senha</label>
            <button type="button" onClick={() => setMostrarSenha((current) => !current)}>
              {mostrarSenha ? "Ocultar" : "Mostrar"}
            </button>
          </div>
          <input
            type={mostrarSenha ? "text" : "password"}
            placeholder="Digite sua senha"
            value={senha}
            onChange={(event) => setSenha(event.target.value)}
            autoComplete="current-password"
            required
          />

          <button className="login-submit" type="submit" disabled={carregando}>
            {carregando ? "Entrando..." : "Entrar"}
          </button>

          <div className="login-footer-links">
            <Link to="/cadastro">Criar conta</Link>
            <Link to="/catalogo">Ver catálogo</Link>
            <Link to="/">Voltar ao início</Link>
          </div>
        </form>
      </section>
    </div>
  );
}
