import { Link } from "react-router-dom";
import CompletarCadastroCliente from "../../components/CompletarCadastroCliente";
import { ErrorMessage, Loading, PageHeader } from "../../components/ui";
import useClienteLogado from "../../hooks/useClienteLogado";

export default function AreaCliente() {
  const { cliente, loadingCliente, clienteError, cadastrarClienteLogado } = useClienteLogado();

  return (
    <div>
      <PageHeader
        title="Área do Cliente"
        description="Acompanhe seus equipamentos e suas ordens de serviço."
      />

      {loadingCliente && <Loading text="Carregando dados do cliente..." />}
      {cliente && <ErrorMessage message={clienteError} />}

      {!loadingCliente && !cliente && (
        <CompletarCadastroCliente onCadastrar={cadastrarClienteLogado} aviso={clienteError} />
      )}

      {cliente && (
        <section className="table-card detail-card">
          <h2>{cliente.usuario?.nome || "Cliente"}</h2>
          <p><strong>E-mail:</strong> {cliente.usuario?.email || "-"}</p>
          <p><strong>CPF:</strong> {cliente.cpf}</p>
          <p><strong>Telefone:</strong> {cliente.telefone}</p>
          <p><strong>WhatsApp:</strong> {cliente.whatsapp}</p>
          <p><strong>Endereço:</strong> {cliente.endereco}</p>
        </section>
      )}

      <div className="cards-grid">
        <Link className="card nav-card" to="/cliente/equipamentos">
          <span>Equipamentos</span>
          <strong>Abrir</strong>
        </Link>
        <Link className="card nav-card" to="/cliente/ordens-servico">
          <span>Ordens de Serviço</span>
          <strong>Abrir</strong>
        </Link>
        <Link className="card nav-card" to="/catalogo">
          <span>Catálogo Público</span>
          <strong>Abrir</strong>
        </Link>
        <Link className="card nav-card" to="/cliente/carrinho">
          <span>Meu carrinho</span>
          <strong>Abrir</strong>
        </Link>
      </div>
    </div>
  );
}
