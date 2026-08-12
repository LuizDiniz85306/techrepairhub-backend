import { Link } from "react-router-dom";
import { PageHeader } from "../../components/ui";

export default function AreaTecnico() {
  return (
    <div>
      <PageHeader title="Área do Técnico" description="Atalhos para ordens e relatórios técnicos." />
      <div className="cards-grid">
        <Link className="card nav-card" to="/tecnico/minhas-os"><span>Minhas OS</span><strong>Abrir</strong></Link>
        <Link className="card nav-card" to="/tecnico/relatorios"><span>Relatórios</span><strong>Abrir</strong></Link>
      </div>
    </div>
  );
}
