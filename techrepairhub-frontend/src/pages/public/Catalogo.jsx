import { useEffect, useMemo, useState } from "react";
import { Link } from "react-router-dom";
import api from "../../api/axiosConfig";
import { EmptyState, ErrorMessage, Loading, PageHeader } from "../../components/ui";
import { formatCurrency, normalizeError } from "../../utils/formatters";

export default function Catalogo() {
  const [produtos, setProdutos] = useState([]);
  const [categorias, setCategorias] = useState([]);
  const [imagensPorProduto, setImagensPorProduto] = useState({});
  const [categoriaId, setCategoriaId] = useState("");
  const [busca, setBusca] = useState("");
  const [ordenacao, setOrdenacao] = useState("relevancia");
  const [imagensComErro, setImagensComErro] = useState({});
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    async function carregar() {
      try {
        const [produtosResponse, categoriasResponse] = await Promise.all([
          api.get("/produtos/ativos"),
          api.get("/categorias/ativos").catch(() => api.get("/categorias")),
        ]);
        const imagensResponse = await api.get("/imagens-produto").catch(() => ({ data: [] }));
        setProdutos(produtosResponse.data);
        setCategorias(categoriasResponse.data);
        setImagensPorProduto(mapearImagens(imagensResponse.data));
      } catch (err) {
        setError(normalizeError(err, "Erro ao carregar catálogo."));
      } finally {
        setLoading(false);
      }
    }
    carregar();
  }, []);

  const filtrados = useMemo(() => {
    const termo = busca.trim().toLowerCase();
    const lista = produtos.filter((produto) => {
      const categoriaAtual = String(produto.categoria?.id || produto.categoriaId || "");
      const texto = `${produto.nome || ""} ${produto.descricao || ""} ${produto.categoria?.nome || ""}`.toLowerCase();
      const categoriaOk = !categoriaId || categoriaAtual === categoriaId;
      const buscaOk = !termo || texto.includes(termo);
      return categoriaOk && buscaOk;
    });

    return [...lista].sort((a, b) => {
      if (ordenacao === "menor-preco") return Number(a.preco || 0) - Number(b.preco || 0);
      if (ordenacao === "maior-preco") return Number(b.preco || 0) - Number(a.preco || 0);
      return String(a.nome || "").localeCompare(String(b.nome || ""));
    });
  }, [produtos, categoriaId, busca, ordenacao]);

  const categoriaSelecionada = categorias.find((categoria) => String(categoria.id) === categoriaId);

  function normalizarImagem(url) {
    if (!url) return "";
    try {
      const parsed = new URL(url);
      return parsed.searchParams.get("imgurl") || url;
    } catch {
      return url;
    }
  }

  function mapearImagens(imagens) {
    return imagens
      .filter((imagem) => imagem?.ativo !== false)
      .reduce((acc, imagem) => {
        const produtoId = imagem.produto?.id || imagem.produtoId;
        if (!produtoId) return acc;
        const atual = acc[produtoId];
        if (!atual || imagem.imagemPrincipal) {
          acc[produtoId] = imagem;
        }
        return acc;
      }, {});
  }

  function imagemProduto(produto) {
    const imagem = imagensPorProduto[produto.id];
    return normalizarImagem(
      imagem?.urlImagem || produto.imagemUrl || produto.urlImagem || produto.imagem || produto.fotoUrl || ""
    );
  }

  function fallbackImagem(produto) {
    return (
      <div className="product-image-fallback">
        <span>{produto.nome?.slice(0, 2) || "TR"}</span>
        <small>Imagem em cadastro</small>
      </div>
    );
  }

  return (
    <div className="market-page">
      <section className="market-hero">
        <div>
          <span className="market-kicker">Marketplace TechRepair</span>
          <h1>Catálogo técnico com produtos revisados e acessórios selecionados</h1>
          <p>Consulte notebooks, celulares, periféricos e itens de apoio com preço, categoria e detalhes técnicos em uma vitrine direta.</p>
          <div className="market-hero-stats">
            <span><strong>{produtos.length}</strong> produtos ativos</span>
            <span><strong>{categorias.length}</strong> categorias</span>
            <span><strong>{Object.keys(imagensPorProduto).length}</strong> com imagem</span>
          </div>
        </div>
        <div className="market-hero-card">
          <span className="hero-card-label">Condição especial</span>
          <strong>15% no Pix</strong>
          <span>Entre para comprar e acompanhar pedidos pela área do cliente.</span>
          <div className="hero-card-actions">
            <Link to="/cadastro">Criar conta</Link>
            <Link to="/login">Entrar</Link>
          </div>
        </div>
      </section>

      <section className="market-service-strip">
        <span>Produtos revisados</span>
        <span>Garantia informada</span>
        <span>Filtros por categoria</span>
        <span>Detalhes técnicos</span>
      </section>

      <PageHeader title="Catálogo" description="Produtos disponíveis para consulta pública." />
      <ErrorMessage message={error} />

      <div className="market-toolbar">
        <label className="market-search">
          <span>Buscar produto</span>
          <input
            value={busca}
            onChange={(e) => setBusca(e.target.value)}
            placeholder="Notebook, fonte, memória, acessório..."
          />
        </label>
        <label>
          <span>Ordenar</span>
          <select value={ordenacao} onChange={(e) => setOrdenacao(e.target.value)}>
            <option value="relevancia">Nome A-Z</option>
            <option value="menor-preco">Menor preço</option>
            <option value="maior-preco">Maior preço</option>
          </select>
        </label>
        <div className="market-account-actions">
          <Link className="market-outline-button" to="/login">Entrar</Link>
          <Link className="market-login-button" to="/cadastro">Criar conta</Link>
        </div>
      </div>

      <div className="market-category-row">
        <button
          type="button"
          className={!categoriaId ? "active" : ""}
          onClick={() => setCategoriaId("")}
        >
          Todos
        </button>
        {categorias.map((categoria) => (
          <button
            type="button"
            key={categoria.id}
            className={String(categoria.id) === categoriaId ? "active" : ""}
            onClick={() => setCategoriaId(String(categoria.id))}
          >
            {categoria.nome}
          </button>
        ))}
      </div>

      <div className="market-results-line">
        <div>
          <strong>{filtrados.length}</strong>
          <span>{filtrados.length === 1 ? "produto encontrado" : "produtos encontrados"}</span>
        </div>
        <small>{categoriaSelecionada ? `Categoria: ${categoriaSelecionada.nome}` : "Todos os produtos ativos"}</small>
      </div>

      {loading ? <Loading /> : filtrados.length === 0 ? <EmptyState /> : (
        <div className="catalog-grid">
          {filtrados.map((produto) => {
            const imagem = imagemProduto(produto);
            const imagemFalhou = imagensComErro[produto.id];
            return (
              <Link
                className="product-card"
                to={`/catalogo/produto/${produto.id}`}
                key={produto.id}
                aria-label={`Ver produto ${produto.nome}`}
              >
                <div className="product-image-box">
                  {imagem && !imagemFalhou ? (
                    <img
                      src={imagem}
                      alt={produto.nome}
                      onError={() => setImagensComErro((current) => ({ ...current, [produto.id]: true }))}
                    />
                  ) : (
                    fallbackImagem(produto)
                  )}
                  <div className="product-labels">
                    <small>15% no Pix</small>
                    <small>{produto.estadoConservacao || "Disponível"}</small>
                  </div>
                </div>
                <div className="product-card-body">
                  <span className="product-category">{produto.categoria?.nome || "Sem categoria"}</span>
                  <strong>{produto.nome}</strong>
                  <p>{produto.descricao || "Produto disponível para consulta no catálogo."}</p>
                  <div className="product-meta-row">
                    <span>{produto.garantiaMeses ? `${produto.garantiaMeses} meses garantia` : "Garantia sob consulta"}</span>
                    <span>{produto.estadoConservacao || "Verificado"}</span>
                  </div>
                  <div className="product-price-row">
                    <b>{formatCurrency(produto.preco)}</b>
                    <span>ou consulte condições</span>
                  </div>
                  <span className="product-card-button">Ver produto</span>
                </div>
              </Link>
            );
          })}
        </div>
      )}
    </div>
  );
}
