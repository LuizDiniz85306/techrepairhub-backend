import { createContext, useContext, useState } from "react";
import api from "../api/axiosConfig";

const AuthContext = createContext();

function decodeTokenPayload(token) {
  if (!token) return {};
  try {
    const payload = token.split(".")[1];
    const normalized = payload.replace(/-/g, "+").replace(/_/g, "/");
    return JSON.parse(atob(normalized));
  } catch (error) {
    return {};
  }
}

export function AuthProvider({ children }) {
  const storedToken = localStorage.getItem("token");
  const tokenPayload = decodeTokenPayload(storedToken);
  const [token, setToken] = useState(storedToken);
  const [perfil, setPerfil] = useState(localStorage.getItem("perfil"));
  const [nome, setNome] = useState(localStorage.getItem("nome"));
  const [usuarioId, setUsuarioId] = useState(
    localStorage.getItem("usuarioId") || tokenPayload.id || ""
  );
  const [email, setEmail] = useState(
    localStorage.getItem("email") || tokenPayload.sub || ""
  );

  async function login(email, senha) {
    const response = await api.post("/auth/login", {
      email,
      senha,
    });

    const data = response.data;
    const perfilNormalizado = String(data.perfil || "");
    const usuarioIdNormalizado = String(data.usuarioId || data.id || "");
    const emailNormalizado = String(data.email || email || "");

    localStorage.setItem("token", data.token);
    localStorage.setItem("perfil", perfilNormalizado);
    localStorage.setItem("nome", data.nome);
    localStorage.setItem("usuarioId", usuarioIdNormalizado);
    localStorage.setItem("email", emailNormalizado);

    setToken(data.token);
    setPerfil(perfilNormalizado);
    setNome(data.nome);
    setUsuarioId(usuarioIdNormalizado);
    setEmail(emailNormalizado);

    return { ...data, perfil: perfilNormalizado };
  }

  function logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("perfil");
    localStorage.removeItem("nome");
    localStorage.removeItem("usuarioId");
    localStorage.removeItem("email");

    setToken(null);
    setPerfil(null);
    setNome(null);
    setUsuarioId("");
    setEmail("");

    window.location.href = "/login";
  }

  const autenticado = !!token;

  return (
    <AuthContext.Provider
      value={{
        token,
        perfil,
        nome,
        usuarioId,
        email,
        autenticado,
        login,
        logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  return useContext(AuthContext);
}
