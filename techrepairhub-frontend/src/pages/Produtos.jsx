import { useEffect, useState } from "react";
import api from "../api/axiosConfig";
import ResourcePage from "../components/ResourcePage";
import { EmptyState, ErrorMessage, FormCard, SuccessMessage, TableCard } from "../components/ui";
import { ESTADOS_CONSERVACAO } from "../config/options";
import { normalizeError } from "../utils/formatters";

function normalizarImagem(url) {
  if (!url) return "";
  try {
    const parsed = new URL(url);
    return parsed.searchParams.get("imgurl") || url;
  } catch {
    return url;
  }
}

function produtoIdDoItem(item) {
  return item.produto?.id || item.produtoId;
}

function nomeProduto(item, produtos) {
  const produtoId = produtoIdDoItem(item);
  return item.produto?.nome || produtos.find((produto) => produto.id === produtoId)?.nome || produtoId || "-";
}

function ProdutoExtras({ produtos }) {
  const [imagens, setImagens] = useState([]);
  const [especificacoes, setEspecificacoes] = useState([]);
  const [imagemForm, setImagemForm] = useState({ produtoId: "", urlImagem: "", imagemPrincipal: false });
  const [especificacaoForm, setEspecificacaoForm] = useState({ produtoId: "", nome: "", valor: "" });
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  async function carregarExtras() {
    setError("");
    try {
      const [imagensResponse, especificacoesResponse] = await Promise.all([
        api.get("/imagens-produto"),
        api.get("/especificacoes-produto"),
      ]);
      setImagens(imagensResponse.data);
      setEspecificacoes(especificacoesResponse.data);
    } catch (err) {
      setError(normalizeError(err, "Erro ao carregar imagens/especificacoes."));
    }
  }

  useEffect(() => {
    carregarExtras();
  }, []);

  async function cadastrarImagem(event) {
    event.preventDefault();
    setError("");
    setSuccess("");
    try {
      await api.post("/imagens-produto", {
        produtoId: Number(imagemForm.produtoId),
        urlImagem: normalizarImagem(imagemForm.urlImagem.trim()),
        imagemPrincipal: Boolean(imagemForm.imagemPrincipal),
      });
      setImagemForm({ produtoId: "", urlImagem: "", imagemPrincipal: false });
      setSuccess("Imagem cadastrada.");
      await carregarExtras();
    } catch (err) {
      setError(normalizeError(err, "Erro ao cadastrar imagem."));
    }
  }

  async function alterarImagem(endpoint, mensagem) {
    setError("");
    setSuccess("");
    try {
      await api.put(endpoint);
      setSuccess(mensagem);
      await carregarExtras();
    } catch (err) {
      setError(normalizeError(err, "Erro ao atualizar imagem."));
    }
  }

  async function cadastrarEspecificacao(event) {
    event.preventDefault();
    setError("");
    setSuccess("");
    try {
      await api.post("/especificacoes-produto", {
        produtoId: Number(especificacaoForm.produtoId),
        nome: especificacaoForm.nome,
        valor: especificacaoForm.valor,
      });
      setEspecificacaoForm({ produtoId: "", nome: "", valor: "" });
      setSuccess("Especificacao cadastrada.");
      await carregarExtras();
    } catch (err) {
      setError(normalizeError(err, "Erro ao cadastrar especificacao."));
    }
  }

  async function alterarEspecificacao(endpoint, mensagem) {
    setError("");
    setSuccess("");
    try {
      await api.put(endpoint);
      setSuccess(mensagem);
      await carregarExtras();
    } catch (err) {
      setError(normalizeError(err, "Erro ao atualizar especificacao."));
    }
  }

  function renderProdutoOptions() {
    return produtos.map((produto) => (
      <option key={produto.id} value={produto.id}>
        {produto.nome}
        {produto.ativo === false ? " (inativo)" : ""}
      </option>
    ));
  }

  const imagemPreview = normalizarImagem(imagemForm.urlImagem.trim());

  return (
    <div className="product-extra-grid">
      <ErrorMessage message={error} />
      <SuccessMessage message={success} />

      <FormCard title="Imagem do produto">
        <form onSubmit={cadastrarImagem}>
          <label>Produto</label>
          <select
            value={imagemForm.produtoId}
            onChange={(event) => setImagemForm({ ...imagemForm, produtoId: event.target.value })}
            required
          >
            <option value="">Selecione</option>
            {renderProdutoOptions()}
          </select>

          <label>URL da imagem</label>
          <input
            value={imagemForm.urlImagem}
            onChange={(event) => setImagemForm({ ...imagemForm, urlImagem: event.target.value })}
            placeholder="Cole a URL direta da imagem"
            required
          />
          <small className="field-help">
            Use o endereco direto do arquivo da imagem. Se copiar do Google Imagens, o sistema tenta extrair a imagem real.
          </small>

          {imagemPreview && (
            <div className="image-url-preview">
              <img
                src={imagemPreview}
                alt="Preview da imagem"
                onError={(event) => {
                  event.currentTarget.style.display = "none";
                }}
              />
              <span>Preview da imagem</span>
            </div>
          )}

          <label className="checkbox-line">
            <input
              type="checkbox"
              checked={imagemForm.imagemPrincipal}
              onChange={(event) => setImagemForm({ ...imagemForm, imagemPrincipal: event.target.checked })}
            />
            Imagem principal
          </label>

          <button type="submit">Cadastrar imagem</button>
        </form>
      </FormCard>

      <FormCard title="Especificacao tecnica">
        <form onSubmit={cadastrarEspecificacao}>
          <label>Produto</label>
          <select
            value={especificacaoForm.produtoId}
            onChange={(event) => setEspecificacaoForm({ ...especificacaoForm, produtoId: event.target.value })}
            required
          >
            <option value="">Selecione</option>
            {renderProdutoOptions()}
          </select>

          <label>Nome</label>
          <input
            value={especificacaoForm.nome}
            onChange={(event) => setEspecificacaoForm({ ...especificacaoForm, nome: event.target.value })}
            required
          />

          <label>Valor</label>
          <input
            value={especificacaoForm.valor}
            onChange={(event) => setEspecificacaoForm({ ...especificacaoForm, valor: event.target.value })}
            required
          />

          <button type="submit">Cadastrar especificação</button>
        </form>
      </FormCard>

      <TableCard title="Imagens cadastradas">
        {imagens.length === 0 ? (
          <EmptyState />
        ) : (
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Produto</th>
                <th>Imagem</th>
                <th>URL</th>
                <th>Principal</th>
                <th>Ativa</th>
                <th>Ações</th>
              </tr>
            </thead>
            <tbody>
              {imagens.map((imagem) => {
                const urlImagem = normalizarImagem(imagem.urlImagem);
                const produtoNome = nomeProduto(imagem, produtos);
                return (
                  <tr key={imagem.id}>
                    <td>{imagem.id}</td>
                    <td>{produtoNome}</td>
                    <td>
                      {urlImagem ? (
                        <img
                          className="table-thumb"
                          src={urlImagem}
                          alt={produtoNome}
                          onError={(event) => {
                            event.currentTarget.style.display = "none";
                          }}
                        />
                      ) : (
                        "-"
                      )}
                    </td>
                    <td>
                      <a href={urlImagem || imagem.urlImagem} target="_blank" rel="noreferrer">
                        Abrir
                      </a>
                    </td>
                    <td>{imagem.imagemPrincipal ? "Sim" : "Não"}</td>
                    <td>{imagem.ativo === false ? "Não" : "Sim"}</td>
                    <td>
                      <div className="table-actions">
                        <button
                          type="button"
                          className="btn-secondary"
                          disabled={imagem.imagemPrincipal}
                          onClick={() =>
                            alterarImagem(
                              `/imagens-produto/${imagem.id}/definir-principal`,
                              "Imagem definida como principal."
                            )
                          }
                        >
                          Principal
                        </button>
                        {imagem.ativo === false ? (
                          <button
                            type="button"
                            className="btn-secondary"
                            onClick={() => alterarImagem(`/imagens-produto/${imagem.id}/reativar`, "Imagem reativada.")}
                          >
                            Reativar
                          </button>
                        ) : (
                          <button
                            type="button"
                            className="btn-danger"
                            onClick={() => alterarImagem(`/imagens-produto/${imagem.id}/inativar`, "Imagem inativada.")}
                          >
                            Inativar
                          </button>
                        )}
                      </div>
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        )}
      </TableCard>

      <TableCard title="Especificações cadastradas">
        {especificacoes.length === 0 ? (
          <EmptyState />
        ) : (
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Produto</th>
                <th>Nome</th>
                <th>Valor</th>
                <th>Ativa</th>
                <th>Ações</th>
              </tr>
            </thead>
            <tbody>
              {especificacoes.map((item) => (
                <tr key={item.id}>
                  <td>{item.id}</td>
                  <td>{nomeProduto(item, produtos)}</td>
                  <td>{item.nome}</td>
                  <td>{item.valor}</td>
                  <td>{item.ativo === false ? "Não" : "Sim"}</td>
                  <td>
                    <div className="table-actions">
                      {item.ativo === false ? (
                        <button
                          type="button"
                          className="btn-secondary"
                          onClick={() =>
                            alterarEspecificacao(`/especificacoes-produto/${item.id}/reativar`, "Especificação reativada.")
                          }
                        >
                          Reativar
                        </button>
                      ) : (
                        <button
                          type="button"
                          className="btn-danger"
                          onClick={() =>
                            alterarEspecificacao(`/especificacoes-produto/${item.id}/inativar`, "Especificação inativada.")
                          }
                        >
                          Inativar
                        </button>
                      )}
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </TableCard>
    </div>
  );
}

export default function Produtos() {
  return (
    <ResourcePage
      title="Produtos"
      description="Cadastro, consulta e dados complementares de produtos."
      listEndpoint="/produtos"
      createEndpoint="/produtos"
      loadOptions={{ categorias: "/categorias" }}
      fields={[
        { name: "nome", label: "Nome" },
        { name: "descricao", label: "Descrição", type: "textarea", optional: true },
        { name: "preco", label: "Preço", type: "number", step: "0.01", min: "0" },
        { name: "estadoConservacao", label: "Estado de conservação", type: "select", options: ESTADOS_CONSERVACAO },
        { name: "garantiaMeses", label: "Garantia em meses", type: "number", min: "0" },
        {
          name: "categoriaId",
          label: "Categoria",
          type: "select",
          optionsKey: "categorias",
          optionValue: (categoria) => categoria.id,
          optionLabel: (categoria) => `${categoria.nome}${categoria.ativo === false ? " (inativa)" : ""}`,
        },
      ]}
      columns={[
        { key: "id", label: "ID" },
        { key: "nome", label: "Nome" },
        { key: "descricao", label: "Descrição" },
        { key: "preco", label: "Preço", type: "currency" },
        { key: "categoria.nome", label: "Categoria" },
        { key: "estadoConservacao", label: "Estado", type: "status" },
        { key: "garantiaMeses", label: "Garantia" },
        { key: "ativo", label: "Ativo", type: "boolean" },
      ]}
      actions={[
        {
          label: "Inativar",
          endpoint: (item) => `/produtos/${item.id}/inativar`,
          danger: true,
          disabled: (item) => item.ativo === false,
        },
        {
          label: "Reativar",
          endpoint: (item) => `/produtos/${item.id}/reativar`,
          disabled: (item) => item.ativo !== false,
        },
      ]}
      extraContent={({ items }) => <ProdutoExtras produtos={items} />}
    />
  );
}
