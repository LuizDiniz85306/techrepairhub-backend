import { Navigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import { getHomeRouteByPerfil } from "../utils/authRoutes";

export default function ProtectedRoute({ children, perfis }) {
  const { autenticado, perfil } = useAuth();

  if (!autenticado) {
    return <Navigate to="/login" replace />;
  }

  if (perfis?.length && !perfis.includes(perfil)) {
    return <Navigate to={getHomeRouteByPerfil(perfil)} replace />;
  }

  return children;
}
