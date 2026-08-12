export function getHomeRouteByPerfil(perfil) {
  switch (perfil) {
    case "ADMIN":
    case "GESTOR":
      return "/dashboard";
    case "ATENDENTE":
      return "/clientes";
    case "TECNICO":
      return "/tecnico";
    case "CLIENTE":
      return "/cliente";
    default:
      return "/catalogo";
  }
}
