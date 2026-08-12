import { Navigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import { getHomeRouteByPerfil } from "../utils/authRoutes";

export default function HomeRedirect() {
  const { perfil } = useAuth();
  return <Navigate to={getHomeRouteByPerfil(perfil)} replace />;
}
