export function getId(item) {
  return (
    item?.id ??
    item?.usuarioId ??
    item?.clienteId ??
    item?.tecnicoId ??
    item?.produtoId ??
    item?.pedidoId ??
    item?.ordemServicoId ??
    item?.equipamentoId ??
    item?.garantiaId ??
    item?.historicoId ??
    item?.carrinhoId ??
    "-"
  );
}

export function valueAt(item, path) {
  if (!path) return "";
  return path.split(".").reduce((value, key) => value?.[key], item);
}

export function firstValue(item, paths, fallback = "-") {
  for (const path of paths) {
    const value = valueAt(item, path);
    if (value !== undefined && value !== null && value !== "") return value;
  }
  return fallback;
}

export function formatCurrency(value) {
  if (value === undefined || value === null || value === "") return "-";
  const number = Number(value);
  if (Number.isNaN(number)) return value;
  return number.toLocaleString("pt-BR", {
    style: "currency",
    currency: "BRL",
  });
}

export function formatDate(value) {
  if (!value) return "-";
  const date = new Date(value);
  if (Number.isNaN(date.getTime())) return value;
  return date.toLocaleString("pt-BR");
}

export function formatBoolean(value) {
  if (value === true) return "Sim";
  if (value === false) return "Não";
  return "-";
}

export function normalizeError(error, fallback = "Não foi possível concluir a operação.") {
  const data = error?.response?.data;
  if (typeof data === "string") return data;
  return (
    data?.message ||
    data?.mensagem ||
    data?.erro ||
    data?.error ||
    error?.message ||
    fallback
  );
}

export function toDateTimeLocal(date) {
  const value = date ? new Date(date) : new Date();
  value.setMinutes(value.getMinutes() - value.getTimezoneOffset());
  return value.toISOString().slice(0, 16);
}
