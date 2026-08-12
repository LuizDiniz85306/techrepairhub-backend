import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import api from "../../api/axiosConfig";
import { EmptyState, ErrorMessage, Loading, PageHeader, SuccessMessage } from "../../components/ui";
import { useAuth } from "../../context/AuthContext";
import { formatCurrency, normalizeError } from "../../utils/formatters";

export default function ProdutoDetalhe() {
  const { id } = useParams();
  const { autenticado, perfil } = useAuth();
  const [produto, setProduto] = useState(null);
  const [imagens, setImagens] = useState([]);
  const [imagemAtiva, setImagemAtiva] = useState("");
  const [imagemFalhou, setImagemFalhou] = useState(false);
  const [especificacoes, setEspecificacoes] = useState([]);
  const [quantidade, setQuantidade] = useState(1);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  useEffect(() => {
    async function carregar() {
      try {
        const [produtoResponse, imagensResponse, especificacoesResponse] = await Promise.all([
          api.get(`/produtos/${id}`),
          api.get(`/imagens-produto/produto/${id}/ativas`).catch(() => ({ data: [] })),
          api.get(`/especificacoes-produto/produto/${id}/ativas`).catch(() => ({ data: [] })),
        ]);
        setProduto(produtoResponse.data);
        const imagensOrdenadas = ordenarImagens(imagensResponse.data);
        setImagens(imagensOrdenadas);
        setImagemAtiva(normalizarImagem(imagensOrdenadas[0]?.urlImagem));
        setImagemFalhou(false);
        setEspecificacoes(especificacoesResponse.data);
      } catch (err) {
        setError(normalizeError(err, "Erro ao carregar produto."));
      } finally {
        setLoading(false);
      }
    }
    carregar();
  }, [id]);

  function normalizarImagem(url) {
    if (!url) return "";
    try {
      const parsed = new URL(url);
      return parsed.searchParams.get("imgurl") || url;
    } catch {
      return url;
    }
  }

  function ordenarImagens(lista) {
    return [...lista].sort((a, b) => Number(Boolean(b.imagemPrincipal)) - Number(Boolean(a.imagemPrincipal)));
  }

  async function adicionarAoCarrinho() {
    setError("");
    setSuccess("");
    try {
      await api.post("/carrinhos/meu/itens", {
        produtoId: Number(id),
        quantidade: Number(quantidade),
      });
      setSuccess("Produto adicionado ao carrinho.");
    } catch (err) {
      setError(normalizeError(err, "Erro ao adicionar produto ao carrinho."));
    }
  }

  if (loading) return <Loading />;

  const imagemPrincipal = imagemAtiva || normalizarImagem(imagens[0]?.urlImagem);
  const podeComprar = autenticado && perfil === "CLIENTE";

  return (
    <div className="market-page">
      <PageHeader title={produto?.nome || "Produto"} description="Detalhes do produto." />
      <ErrorMessage message={error} />
      <SuccessMessage message={success} />
      {!produto ? <EmptyState /> : (
        <section className="product-detail market-product-detail">
          <div className="detail-gallery">
            {imagemPrincipal && !imagemFalhou ? (
              <img
                src={imagemPrincipal}
                alt={produto.nome}
                onError={() => setImagemFalhou(true)}
              />
            ) : (
              <div className="detail-placeholder">{produto.nome?.slice(0, 2) || "TR"}</div>
            )}
            {imagens.length > 1 && (
              <div className="detail-thumbs">
                {imagens.map((imagem) => {
                  const urlImagem = normalizarImagem(imagem.urlImagem);
                  return (
                    <button
                      type="button"
                      key={imagem.id}
                      className={urlImagem === imagemPrincipal && !imagemFalhou ? "active" : ""}
                      onClick={() => {
                        setImagemAtiva(urlImagem);
                        setImagemFalhou(false);
                      }}
                    >
                      <img
                        src={urlImagem}
                        alt={produto.nome}
                        onError={(event) => {
                          event.currentTarget.style.display = "none";
                        }}
                      />
                    </button>
                  );
                })}
              </div>
            )}
          </div>
          <div className="detail-info">
            <span className="product-category">{produto.categoria?.nome || "Catálogo TechRepair"}</span>
            <h1>{produto.nome}</h1>
            <p>{produto.descricao || "Produto disponível para consulta no catálogo."}</p>

            <div className="detail-price-box">
              <span>Preço</span>
              <strong>{formatCurrency(produto.preco)}</strong>
              <small>15% de desconto no Pix conforme disponibilidade.</small>
            </div>

            <div className="detail-badges">
              <span>Produto verificado</span>
              <span>Suporte técnico</span>
              <span>Garantia: {produto.garantiaMeses || "-"} meses</span>
            </div>

            <div className="detail-specs">
              <h2>Especificações</h2>
              {especificacoes.length === 0 ? <EmptyState /> : (
                <ul>{especificacoes.map((item) => <li key={item.id}><strong>{item.nome}</strong><span>{item.valor}</span></li>)}</ul>
              )}
            </div>

            <div className="detail-actions">
              {podeComprar ? (
                <div className="cart-buy-panel">
                  <label>
                    Quantidade
                    <input
                      type="number"
                      min="1"
                      value={quantidade}
                      onChange={(event) => setQuantidade(event.target.value)}
                    />
                  </label>
                  <button type="button" className="market-buy-button" onClick={adicionarAoCarrinho}>
                    Adicionar ao carrinho
                  </button>
                  <Link to="/cliente/carrinho" className="market-outline-button">Ver carrinho</Link>
                </div>
              ) : (
                <Link to="/login" className="market-buy-button">Entrar para comprar</Link>
              )}
              <Link to="/catalogo" className="market-outline-button">Voltar ao catálogo</Link>
            </div>
          </div>
        </section>
      )}
    </div>
  );
}
