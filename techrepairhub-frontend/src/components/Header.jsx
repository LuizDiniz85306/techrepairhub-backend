import { useAuth } from "../context/AuthContext";

export default function Header() {
  const { nome, perfil, logout } = useAuth();

  return (
    <header className="header">
      <div>
        <h2>Área Interna</h2>
        <span>Bem-vindo, {nome || "Usuário"}</span>
      </div>

      <div className="header-user">
        <strong>{perfil || "PERFIL"}</strong>
        <button onClick={logout}>Sair</button>
      </div>
    </header>
  );
}