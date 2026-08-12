import { useEffect, useState } from "react";
import api from "../api/axiosConfig";
import { useAuth } from "../context/AuthContext";
import { normalizeError } from "../utils/formatters";

export default function useTecnicoLogado() {
  const { usuarioId, perfil } = useAuth();
  const [tecnico, setTecnico] = useState(null);
  const [loadingTecnico, setLoadingTecnico] = useState(true);
  const [tecnicoError, setTecnicoError] = useState("");

  useEffect(() => {
    async function carregarTecnico() {
      setLoadingTecnico(true);
      setTecnicoError("");

      if (!usuarioId || perfil !== "TECNICO") {
        setLoadingTecnico(false);
        return;
      }

      try {
        const response = await api.get("/tecnicos/me");
        setTecnico(response.data);
        localStorage.setItem(`tecnicoId:${usuarioId}`, String(response.data.id));
      } catch (err) {
        const storedId = localStorage.getItem(`tecnicoId:${usuarioId}`) || localStorage.getItem("tecnicoId");
        if (storedId) {
          setTecnico({ id: Number(storedId) });
          setTecnicoError("");
          return;
        }

        setTecnicoError(
          normalizeError(
            err,
            "Não foi possível identificar automaticamente o técnico. Informe seu ID de técnico para listar e assumir OS."
          )
        );
      } finally {
        setLoadingTecnico(false);
      }
    }

    carregarTecnico();
  }, [perfil, usuarioId]);

  function salvarTecnicoManual(tecnicoId) {
    const id = Number(tecnicoId);
    if (!id) return;
    localStorage.setItem("tecnicoId", String(id));
    if (usuarioId) localStorage.setItem(`tecnicoId:${usuarioId}`, String(id));
    setTecnico({ id });
    setTecnicoError("");
  }

  return { tecnico, loadingTecnico, tecnicoError, salvarTecnicoManual };
}
