import { Link } from "react-router-dom";
import { PageHeader } from "../components/ui";

export default function Financeiro() {
  return (
    <div>
      <PageHeader title="Financeiro" description="Acesse receitas, despesas e fluxo de caixa." />
      <div className="cards-grid">
        <Link className="card nav-card" to="/financeiro/receitas"><span>Receitas</span><strong>Abrir</strong></Link>
        <Link className="card nav-card" to="/financeiro/despesas"><span>Despesas</span><strong>Abrir</strong></Link>
        <Link className="card nav-card" to="/financeiro/fluxo-caixa"><span>Fluxo de Caixa</span><strong>Abrir</strong></Link>
      </div>
    </div>
  );
}
