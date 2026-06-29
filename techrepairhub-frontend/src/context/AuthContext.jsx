import { createContext, useContext, useState } from "react";
import api from "../api/axiosConfig";

const AuthContext = createContext();

export function AuthProvider({ children }) {
  const [token, setToken] = useState(localStorage.getItem("token"));
  const [perfil, setPerfil] = useState(localStorage.getItem("perfil"));
  const [nome, setNome] = useState(localStorage.getItem("nome"));

  async function login(email, senha) {
    const response = await api.post("/auth/login", {
      email,
      senha,
    });

    const data = response.data;

    localStorage.setItem("token", data.token);
    localStorage.setItem("perfil", data.perfil);
    localStorage.setItem("nome", data.nome);

    setToken(data.token);
    setPerfil(data.perfil);
    setNome(data.nome);

    return data;
  }

  function logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("perfil");
    localStorage.removeItem("nome");

    setToken(null);
    setPerfil(null);
    setNome(null);

    window.location.href = "/login";
  }

  const autenticado = !!token;

  return (
    <AuthContext.Provider
      value={{
        token,
        perfil,
        nome,
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