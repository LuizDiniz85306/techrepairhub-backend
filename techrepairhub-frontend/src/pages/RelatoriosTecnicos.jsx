import ResourcePage from "../components/ResourcePage";

export default function RelatoriosTecnicos() {
  return (
    <ResourcePage
      title="Relatórios Técnicos"
      description="Laudos e relatórios vinculados a ordens de serviço."
      listEndpoint="/relatorios-tecnicos"
      createEndpoint="/relatorios-tecnicos"
      loadOptions={{ ordens: "/ordens-servico", tecnicos: "/tecnicos" }}
      fields={[
        { name: "ordemServicoId", label: "Ordem de serviço", type: "select", optionsKey: "ordens", optionValue: (os) => os.ordemServicoId, optionLabel: (os) => `OS ${os.ordemServicoId} - ${os.nomeCliente}` },
        { name: "tecnicoId", label: "Técnico", type: "select", optionsKey: "tecnicos", optionValue: (t) => t.id, optionLabel: (t) => t.usuario?.nome || t.nome || `Técnico ${t.id}` },
        { name: "problemaRelatado", label: "Problema relatado", type: "textarea" },
        { name: "diagnostico", label: "Diagnóstico", type: "textarea" },
        { name: "procedimentosExecutados", label: "Procedimentos executados", type: "textarea" },
        { name: "testesEfetuados", label: "Testes efetuados", type: "textarea", optional: true },
        { name: "resultadoObtido", label: "Resultado obtido", type: "textarea", optional: true },
        { name: "observacoesAdicionais", label: "Observações adicionais", type: "textarea", optional: true },
      ]}
      columns={[
        { key: "id", label: "ID" },
        { key: "ordemServicoId", label: "OS" },
        { key: "tecnico", label: "Técnico" },
        { key: "cliente", label: "Cliente" },
        { key: "equipamento", label: "Equipamento" },
        { key: "diagnostico", label: "Diagnóstico" },
        { key: "dataRelatorio", label: "Data", type: "date" },
      ]}
    />
  );
}
