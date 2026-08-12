import ResourcePage from "../components/ResourcePage";

export default function Permissoes() {
  return (
    <ResourcePage
      title="Permissões"
      description="Consulta de permissões por perfil."
      listEndpoint="/permissoes"
      createEndpoint={null}
      noCreateMessage="Permissões são expostas apenas para consulta pelo back-end."
      columns={[
        { key: "perfil", label: "Perfil", type: "status" },
        { key: "descricao", label: "Descrição" },
        { label: "Permissões", render: (item) => item.permissoes?.join(", ") },
        { label: "Módulos", render: (item) => item.modulosPermitidos?.join(", ") },
        { label: "Rotas", render: (item) => item.rotasPermitidas?.join(", ") },
      ]}
    />
  );
}
