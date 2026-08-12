import { Link } from "react-router-dom";
import { useEffect, useState } from "react";
import api from "../../api/axiosConfig";
import {
  EmptyState,
  ErrorMessage,
  FormCard,
  Loading,
  PageHeader,
  StatusBadge,
  SuccessMessage,
  TableCard,
} from "../../components/ui";
import useTecnicoLogado from "../../hooks/useTecnicoLogado";
import { formatCurrency, formatDate, normalizeError } from "../../utils/formatters";

const STATUS_FECHADOS = ["FINALIZADA", "CANCELADA"];

function OrdemRow({ ordem, onAssumir, podeAssumir }) {
  const fechada = STATUS_FECHADOS.includes(String(ordem.status));

  return (
    <tr>
      <td>#{ordem.ordemServicoId}</td>
      <td>{ordem.nomeCliente || "-"}</td>
      <td>{ordem.equipamento || "-"}</td>
      <td><StatusBadge value={ordem.status} /></td>
      <td>{formatCurrency(ordem.valorTotal)}</td>
      <td>{formatDate(ordem.dataAbertura)}</td>
      <td>
        <div className="table-actions">
          <Link className="btn-secondary" to={`/tecnico/minhas-os/${ordem.ordemServicoId}`}>Abrir</Link>
          {podeAssumir && !fechada && (
            <button type="button" className="btn-secondary" onClick={() => onAssumir(ordem)}>
              Assumir
            </button>
          )}
        </div>
      </td>
    </tr>
  );
}

export default function MinhasOrdensServico() {
  const { tecnico, loadingTecnico, tecnicoError, salvarTecnicoManual } = useTecnicoLogado();
  const [tecnicoIdManual, setTecnicoIdManual] = useState("");
  const [minhasOrdens, setMinhasOrdens] = useState([]);
  const [ordensDisponiveis, setOrdensDisponiveis] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  async function carregarOrdens() {
    setLoading(true);
    setError("");
    try {
      const [minhasResponse, todasResponse] = await Promise.all([
        tecnico?.id ? api.get("/ordens-servico/minhas") : Promise.resolve({ data: [] }),
        api.get("/ordens-servico"),
      ]);

      setMinhasOrdens(minhasResponse.data);
      setOrdensDisponiveis(
        todasResponse.data.filter((ordem) => !ordem.tecnicoId && !STATUS_FECHADOS.includes(String(ordem.status)))
      );
    } catch (err) {
      setMinhasOrdens([]);
      setOrdensDisponiveis([]);
      setError(normalizeError(err, "Erro ao carregar ordens de serviço."));
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    if (!loadingTecnico) carregarOrdens();
  }, [loadingTecnico, tecnico?.id]);

  async function assumirOrdem(ordem) {
    if (!tecnico?.id) {
      setError("Informe seu ID de técnico antes de assumir uma OS.");
      return;
    }

    setError("");
    setSuccess("");
    try {
      await api.put(`/ordens-servico/${ordem.ordemServicoId}/atribuir-tecnico/${tecnico.id}`);
      setSuccess(`OS #${ordem.ordemServicoId} atribuída ao seu técnico.`);
      await carregarOrdens();
    } catch (err) {
      setError(normalizeError(err, "Erro ao assumir a ordem de serviço."));
    }
  }

  function salvarManual(event) {
    event.preventDefault();
    salvarTecnicoManual(tecnicoIdManual);
    setTecnicoIdManual("");
  }

  return (
    <div>
      <PageHeader
        title="Minhas Ordens de Serviço"
        description="Ordens atribuídas ao técnico e ordens abertas disponíveis para assumir."
      />

      <ErrorMessage message={error || tecnicoError} />
      <SuccessMessage message={success} />

      {!loadingTecnico && !tecnico?.id && (
        <FormCard title="Identificar técnico">
          <form onSubmit={salvarManual}>
            <label>ID do técnico</label>
            <input
              type="number"
              value={tecnicoIdManual}
              onChange={(event) => setTecnicoIdManual(event.target.value)}
              placeholder="Ex.: 1"
              required
            />
            <button type="submit">Usar este técnico</button>
          </form>
        </FormCard>
      )}

      {loading || loadingTecnico ? (
        <Loading />
      ) : (
        <div className="content-grid tecnico-os-grid">
          <TableCard title="Minhas OS atribuídas">
            {minhasOrdens.length === 0 ? (
              <EmptyState message="Nenhuma OS atribuída ao seu técnico." />
            ) : (
              <table>
                <thead>
                  <tr>
                    <th>OS</th>
                    <th>Cliente</th>
                    <th>Equipamento</th>
                    <th>Status</th>
                    <th>Valor</th>
                    <th>Abertura</th>
                    <th>Ações</th>
                  </tr>
                </thead>
                <tbody>
                  {minhasOrdens.map((ordem) => (
                    <OrdemRow key={ordem.ordemServicoId} ordem={ordem} onAssumir={assumirOrdem} />
                  ))}
                </tbody>
              </table>
            )}
          </TableCard>

          <TableCard title="OS abertas disponíveis">
            {ordensDisponiveis.length === 0 ? (
              <EmptyState message="Nenhuma OS aberta sem técnico no momento." />
            ) : (
              <table>
                <thead>
                  <tr>
                    <th>OS</th>
                    <th>Cliente</th>
                    <th>Equipamento</th>
                    <th>Status</th>
                    <th>Valor</th>
                    <th>Abertura</th>
                    <th>Ações</th>
                  </tr>
                </thead>
                <tbody>
                  {ordensDisponiveis.map((ordem) => (
                    <OrdemRow
                      key={ordem.ordemServicoId}
                      ordem={ordem}
                      onAssumir={assumirOrdem}
                      podeAssumir={Boolean(tecnico?.id)}
                    />
                  ))}
                </tbody>
              </table>
            )}
          </TableCard>
        </div>
      )}
    </div>
  );
}
