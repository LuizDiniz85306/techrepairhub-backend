import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080",
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");

  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});

api.interceptors.response.use(
  (response) => response,
  (error) => {
    const status = error?.response?.status;
    if (status === 401 && window.location.pathname !== "/login") {
      localStorage.removeItem("token");
      localStorage.removeItem("perfil");
      localStorage.removeItem("nome");
      localStorage.removeItem("usuarioId");
      localStorage.removeItem("email");
      window.location.href = "/login";
    }
    return Promise.reject(error);
  }
);

export default api;
