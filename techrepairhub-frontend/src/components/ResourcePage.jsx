import { useEffect, useMemo, useState } from "react";
import api from "../api/axiosConfig";
import {
  EmptyState,
  ErrorMessage,
  FormCard,
  Loading,
  PageHeader,
  SmartValue,
  SuccessMessage,
  TableCard,
} from "./ui";
import { getId, normalizeError } from "../utils/formatters";

function emptyForm(fields) {
  return fields.reduce((acc, field) => {
    acc[field.name] = field.defaultValue ?? "";
    return acc;
  }, {});
}

function coerceValue(value, field) {
  if (field.type === "number" && value !== "") return Number(value);
  if (field.type === "checkbox") return Boolean(value);
  return value;
}

function buildPayload(fields, form) {
  return fields.reduce((acc, field) => {
    const value = form[field.name];
    if (value === "" && field.optional) return acc;
    acc[field.name] = coerceValue(value, field);
    return acc;
  }, {});
}

export default function ResourcePage({
  title,
  description,
  listEndpoint,
  createEndpoint,
  fields = [],
  columns = [],
  tableTitle = "Registros cadastrados",
  formTitle = "Novo registro",
  createLabel = "Cadastrar",
  noCreateMessage = "Funcionalidade ainda não disponível no back-end.",
  loadOptions = {},
  afterLoad,
  transformCreatePayload,
  extraContent,
  actions = [],
}) {
  const [items, setItems] = useState([]);
  const [form, setForm] = useState(() => emptyForm(fields));
  const [options, setOptions] = useState({});
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const visibleFields = useMemo(() => fields.filter((field) => !field.hidden), [fields]);

  async function loadData() {
    setError("");
    setLoading(true);
    try {
      const [listResponse, optionResponses] = await Promise.all([
        listEndpoint ? api.get(listEndpoint) : Promise.resolve({ data: [] }),
        Promise.all(
          Object.entries(loadOptions).map(async ([key, endpoint]) => {
            const response = await api.get(endpoint);
            return [key, response.data];
          })
        ),
      ]);

      setItems(afterLoad ? afterLoad(listResponse.data) : listResponse.data);
      setOptions(Object.fromEntries(optionResponses));
    } catch (err) {
      setError(normalizeError(err, "Erro ao carregar dados."));
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    loadData();
  }, [listEndpoint]);

  function handleChange(event) {
    const { name, type, checked, value } = event.target;
    setForm((current) => ({
      ...current,
      [name]: type === "checkbox" ? checked : value,
    }));
  }

  async function handleSubmit(event) {
    event.preventDefault();
    if (!createEndpoint) return;

    setSaving(true);
    setError("");
    setSuccess("");

    try {
      const payload = buildPayload(fields, form);
      await api.post(
        typeof createEndpoint === "function" ? createEndpoint(payload) : createEndpoint,
        transformCreatePayload ? transformCreatePayload(payload) : payload
      );
      setSuccess("Registro cadastrado com sucesso.");
      setForm(emptyForm(fields));
      await loadData();
    } catch (err) {
      setError(normalizeError(err, "Erro ao cadastrar registro."));
    } finally {
      setSaving(false);
    }
  }

  async function runAction(action, item) {
    setError("");
    setSuccess("");
    try {
      const endpoint = typeof action.endpoint === "function" ? action.endpoint(item) : action.endpoint;
      await api[action.method || "put"](endpoint);
      setSuccess(action.successMessage || "Ação realizada com sucesso.");
      await loadData();
    } catch (err) {
      setError(normalizeError(err));
    }
  }

  function renderField(field) {
    const commonProps = {
      id: field.name,
      name: field.name,
      value: form[field.name],
      onChange: handleChange,
      required: field.required !== false && !field.optional,
    };

    if (field.type === "select") {
      const selectOptions = field.options || options[field.optionsKey] || [];
      return (
        <select {...commonProps}>
          <option value="">{field.placeholder || "Selecione"}</option>
          {selectOptions.map((option) => {
            const value = field.optionValue ? field.optionValue(option) : option.value ?? option.id ?? option;
            const label = field.optionLabel ? field.optionLabel(option) : option.label ?? option.nome ?? String(option);
            return (
              <option key={value} value={value}>
                {label}
              </option>
            );
          })}
        </select>
      );
    }

    if (field.type === "textarea") {
      return <textarea {...commonProps} rows={field.rows || 3} placeholder={field.placeholder} />;
    }

    if (field.type === "checkbox") {
      return (
        <label className="checkbox-line">
          <input
            type="checkbox"
            name={field.name}
            checked={Boolean(form[field.name])}
            onChange={handleChange}
          />
          {field.label}
        </label>
      );
    }

    return (
      <input
        {...commonProps}
        type={field.type || "text"}
        min={field.min}
        step={field.step}
        placeholder={field.placeholder}
      />
    );
  }

  return (
    <div>
      <PageHeader title={title} description={description} />

      <ErrorMessage message={error} />
      <SuccessMessage message={success} />

      <div className="content-grid">
        <FormCard title={formTitle}>
          {createEndpoint ? (
            <form onSubmit={handleSubmit}>
              {visibleFields.map((field) => (
                <div key={field.name}>
                  {field.type !== "checkbox" && <label htmlFor={field.name}>{field.label}</label>}
                  {renderField(field)}
                </div>
              ))}
              <button type="submit" disabled={saving}>
                {saving ? "Salvando..." : createLabel}
              </button>
            </form>
          ) : (
            <div className="notice">{noCreateMessage}</div>
          )}
        </FormCard>

        <TableCard title={tableTitle}>
          {loading ? (
            <Loading />
          ) : items.length === 0 ? (
            <EmptyState />
          ) : (
            <table>
              <thead>
                <tr>
                  {columns.map((column) => (
                    <th key={column.key || column.label}>{column.label}</th>
                  ))}
                  {actions.length > 0 && <th>Ações</th>}
                </tr>
              </thead>
              <tbody>
                {items.map((item) => (
                  <tr key={getId(item)}>
                    {columns.map((column) => (
                      <td key={column.key || column.label}>
                        <SmartValue item={item} column={column} />
                      </td>
                    ))}
                    {actions.length > 0 && (
                      <td>
                        <div className="table-actions">
                          {actions.map((action) => (
                            <button
                              key={action.label}
                              type="button"
                              className={action.danger ? "btn-danger" : "btn-secondary"}
                              onClick={() => runAction(action, item)}
                              disabled={action.disabled?.(item)}
                            >
                              {action.label}
                            </button>
                          ))}
                        </div>
                      </td>
                    )}
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </TableCard>
      </div>

      {extraContent?.({ items, options, reload: loadData, setError, setSuccess })}
    </div>
  );
}
