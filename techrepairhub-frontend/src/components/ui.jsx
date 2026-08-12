import { Link } from "react-router-dom";
import { formatBoolean, formatCurrency, formatDate, valueAt } from "../utils/formatters";
import { statusLabel } from "../utils/statusLabels";

export function Loading({ text = "Carregando..." }) {
  return <p className="muted">{text}</p>;
}

export function EmptyState({ message = "Nenhum registro encontrado." }) {
  return <div className="empty-state">{message}</div>;
}

export function ErrorMessage({ message }) {
  if (!message) return null;
  return <div className="alerta-erro">{message}</div>;
}

export function SuccessMessage({ message }) {
  if (!message) return null;
  return <div className="alerta-sucesso">{message}</div>;
}

export function StatusBadge({ value }) {
  const normalized = String(value ?? "").toUpperCase();
  const label = value === true || value === false ? formatBoolean(value) : statusLabel(value);

  let tone = "neutral";
  if (["ATIVO", "ABERTA", "APROVADO", "PAGO", "LUCRO"].includes(normalized) || value === true) tone = "success";
  if (["EM_ANALISE", "EM_EXECUCAO", "AGUARDANDO_APROVACAO", "AGUARDANDO_ORCAMENTO", "PENDENTE", "EQUILIBRIO"].includes(normalized)) tone = "warning";
  if (["FINALIZADA"].includes(normalized)) tone = "info";
  if (["CANCELADA", "RECUSADO", "INATIVO", "PREJUIZO", "ESTOQUE_BAIXO"].includes(normalized) || value === false) tone = "danger";

  return (
    <span className={`status-badge status-${tone}`}>
      {label}
    </span>
  );
}

export function PageHeader({ title, description, action }) {
  return (
    <div className="page-header">
      <div>
        <h1>{title}</h1>
        {description && <p>{description}</p>}
      </div>
      {action}
    </div>
  );
}

export function FormCard({ title, children }) {
  return (
    <section className="form-card">
      {title && <h2>{title}</h2>}
      {children}
    </section>
  );
}

export function TableCard({ title, children }) {
  return (
    <section className="table-card">
      {title && <h2>{title}</h2>}
      {children}
    </section>
  );
}

export function ConfirmButton({
  children,
  onConfirm,
  message = "Confirma esta ação?",
  disabled,
  className = "btn-secondary",
}) {
  function handleClick() {
    if (disabled) return;
    if (window.confirm(message)) onConfirm?.();
  }

  return (
    <button type="button" className={className} onClick={handleClick} disabled={disabled}>
      {children}
    </button>
  );
}

export function SmartValue({ item, column }) {
  const raw = column.render ? column.render(item) : valueAt(item, column.key);

  if (column.type === "currency") return formatCurrency(raw);
  if (column.type === "date") return formatDate(raw);
  if (column.type === "boolean") return <StatusBadge value={raw} />;
  if (column.type === "status") return <StatusBadge value={raw} />;
  if (column.type === "link") {
    return raw?.to ? <Link to={raw.to}>{raw.label}</Link> : "-";
  }

  return raw ?? "-";
}
