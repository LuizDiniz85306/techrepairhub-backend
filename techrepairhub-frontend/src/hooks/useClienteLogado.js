import { useEffect, useState } from "react";
import api from "../api/axiosConfig";
import { useAuth } from "../context/AuthContext";
import { normalizeError } from "../utils/formatters";

export default function useClienteLogado() {
  const { usuarioId, perfil } = useAuth();
  const [cliente, setCliente] = useState(null);
  const [loadingCliente, setLoadingCliente] = useState(true);
  const [clienteError, setClienteError] = useState("");

  function salvarClienteLocal(clienteEncontrado) {
    if (!clienteEncontrado?.id) return;
    localStorage.setItem("clienteId", String(clienteEncontrado.id));
    if (usuarioId) {
      localStorage.setItem(`clienteId:${usuarioId}`, String(clienteEncontrado.id));
    }
    setCliente(clienteEncontrado);
    setClienteError("");
  }

  async function carregarCliente() {
    setLoadingCliente(true);
    setClienteError("");

    if (perfil !== "CLIENTE") {
      setCliente(null);
      setLoadingCliente(false);
      return;
    }

    if (!usuarioId) {
      setClienteError("Nao foi possivel identificar o usuario logado. Saia e entre novamente.");
      setLoadingCliente(false);
      return;
    }

    try {
      const response = await api.get("/clientes/me");
      salvarClienteLocal(response.data);
    } catch (error) {
      setCliente(null);
      setClienteError(
        normalizeError(error, "Complete seu cadastro de cliente antes de cadastrar equipamentos.")
      );
    } finally {
      setLoadingCliente(false);
    }
  }

  async function cadastrarClienteLogado(dados) {
    const response = await api.post("/clientes/me", dados);
    salvarClienteLocal(response.data);
    return response.data;
  }

  useEffect(() => {
    carregarCliente();
  }, [perfil, usuarioId]);

  return {
    cliente,
    loadingCliente,
    clienteError,
    cadastrarClienteLogado,
    recarregarCliente: carregarCliente,
  };
}
