import { Link, Outlet } from "react-router-dom";

export default function LayoutPublico() {
  return (
    <div className="public-layout">
      <div className="market-topbar">
        <span>Atendimento técnico e produtos para reparo em um só lugar</span>
        <span>Compra segura | Suporte especializado</span>
      </div>
      <header className="public-header">
        <Link to="/" className="public-brand">TechRepair Hub</Link>
        <nav>
          <Link to="/catalogo">Catálogo</Link>
          <Link to="/cadastro">Criar conta</Link>
          <Link to="/login">Login</Link>
        </nav>
      </header>
      <Outlet />
    </div>
  );
}
