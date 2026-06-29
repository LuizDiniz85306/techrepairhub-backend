import { Link } from "react-router-dom";

export default function Sidebar() {
  return (
    <aside className="sidebar">
      <h1>TechRepair</h1>

      <nav>
        <Link to="/dashboard">Dashboard</Link>
        <Link to="/clientes">Clientes</Link>
        <Link to="/produtos">Produtos</Link>
        <Link to="/pedidos">Pedidos</Link>
        <Link to="/ordens-servico">Ordens de Serviço</Link>
        <Link to="/pecas">Peças</Link>
        <Link to="/financeiro">Financeiro</Link>
      </nav>
    </aside>
  );
}