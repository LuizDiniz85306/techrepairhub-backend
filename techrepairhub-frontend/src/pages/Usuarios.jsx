import ResourcePage from "../components/ResourcePage";
import { PERFIS } from "../config/options";

export default function Usuarios() {
  return (
    <ResourcePage
      title="Usuários"
      description="Cadastro e consulta de usuários do sistema."
      listEndpoint="/usuarios"
      createEndpoint="/usuarios"
      fields={[
        { name: "nome", label: "Nome" },
        { name: "email", label: "E-mail", type: "email" },
        { name: "senha", label: "Senha", type: "password" },
        { name: "perfil", label: "Perfil", type: "select", options: PERFIS },
      ]}
      columns={[
        { key: "id", label: "ID" },
        { key: "nome", label: "Nome" },
        { key: "email", label: "E-mail" },
        { key: "perfil", label: "Perfil", type: "status" },
        { key: "ativo", label: "Ativo", type: "boolean" },
      ]}
      actions={[
        {
          label: "Inativar",
          endpoint: (usuario) => `/usuarios/${usuario.id}/inativar`,
          danger: true,
          disabled: (usuario) => usuario.ativo === false,
        },
        {
          label: "Reativar",
          endpoint: (usuario) => `/usuarios/${usuario.id}/reativar`,
          successMessage: "Usuário reativado com sucesso.",
          disabled: (usuario) => usuario.ativo !== false,
        },
      ]}
    />
  );
}
