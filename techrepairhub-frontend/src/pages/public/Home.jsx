import { useEffect, useMemo, useState } from "react";
import { Link } from "react-router-dom";
import api from "../../api/axiosConfig";
import { formatCurrency } from "../../utils/formatters";

const services = [
  {
    title: "Assistência técnica",
    description: "Abra chamados, acompanhe diagnósticos e veja o andamento das ordens de serviço.",
  },
  {
    title: "Marketplace revisado",
    description: "Produtos usados, testados e organizados por categoria para compra com mais confiança.",
  },
  {
    title: "Área do cliente",
    description: "Equipamentos, histórico de OS, pedidos e carrinho ficam em um único ambiente.",
  },
  {
    title: "Estoque técnico",
    description: "Peças e produtos conectados ao fluxo real da assistência e da loja.",
  },
];

const steps = ["Escolha um produto", "Crie sua conta", "Acompanhe tudo online"];

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

export default function Home() {
  const [produtos, setProdutos] = useState([]);
  const [imagensPorProduto, setImagensPorProduto] = useState({});

  useEffect(() => {
    async function carregarProdutos() {
      try {
        const [produtosResponse, imagensResponse] = await Promise.all([
          api.get("/produtos/ativos").catch(() => api.get("/produtos")),
          api.get("/imagens-produto").catch(() => ({ data: [] })),
        ]);
        setProdutos(produtosResponse.data.slice(0, 4));
        setImagensPorProduto(mapearImagens(imagensResponse.data));
      } catch {
        setProdutos([]);
        setImagensPorProduto({});
      }
    }

    carregarProdutos();
  }, []);

  const totalProdutos = produtos.length;
  const destaques = useMemo(() => produtos, [produtos]);

  function imagemProduto(produto) {
    const imagem = imagensPorProduto[produto.id];
    return normalizarImagem(
      imagem?.urlImagem || produto.imagemUrl || produto.urlImagem || produto.imagem || produto.fotoUrl || ""
    );
  }

  return (
    <main className="public-site">
      <section className="public-home">
        <div className="public-hero-copy">
          <span className="market-kicker">TechRepair Hub</span>
          <h1>Assistência técnica e marketplace de produtos revisados</h1>
          <p>
            Compre produtos usados com mais confiança, crie sua conta como cliente e acompanhe equipamentos,
            pedidos e ordens de serviço em uma experiência única.
          </p>
          <div className="hero-actions">
            <Link to="/catalogo">Ver catálogo</Link>
            <Link to="/cadastro" className="secondary-link">Criar conta</Link>
            <Link to="/login" className="ghost-link">Entrar</Link>
          </div>
        </div>

        <aside className="public-hero-panel">
          <span className="hero-card-label">Loja + assistência</span>
          <strong>{totalProdutos || "TR"}</strong>
          <span>produtos em destaque para explorar agora</span>
          <div className="public-step-list">
            {steps.map((step, index) => (
              <div key={step}>
                <b>{index + 1}</b>
                <span>{step}</span>
              </div>
            ))}
          </div>
        </aside>
      </section>

      <section className="public-section">
        <div className="section-title">
          <div>
            <span className="eyebrow">Serviços integrados</span>
            <h2>Uma jornada mais simples para compra e reparo</h2>
            <p>O cliente encontra produtos, cadastra equipamentos e acompanha tudo sem depender de mensagens soltas.</p>
          </div>
        </div>
        <div className="service-grid">
          {services.map((service) => (
            <article className="service-card home-service-card" key={service.title}>
              <h3>{service.title}</h3>
              <p>{service.description}</p>
            </article>
          ))}
        </div>
      </section>

      <section className="public-section public-featured-section">
        <div className="section-title">
          <div>
            <span className="eyebrow">Marketplace</span>
            <h2>Produtos em destaque</h2>
          </div>
          <Link className="section-link" to="/catalogo">Ver todos</Link>
        </div>
        <div className="featured-products">
          {destaques.length === 0 ? (
            <div className="empty-state">Nenhum produto encontrado.</div>
          ) : (
            destaques.map((produto) => {
              const imagem = imagemProduto(produto);
              return (
                <Link className="featured-product-card home-product-card" to={`/catalogo/produto/${produto.id}`} key={produto.id}>
                  <div className="home-product-image">
                    {imagem ? <img src={imagem} alt={produto.nome} loading="lazy" /> : <span>{produto.nome?.slice(0, 2) || "TR"}</span>}
                  </div>
                  <strong>{produto.nome}</strong>
                  <span>{produto.categoria?.nome || "Sem categoria"}</span>
                  <b>{formatCurrency(produto.preco)}</b>
                </Link>
              );
            })
          )}
        </div>
      </section>

      <section className="public-cta">
        <div>
          <span className="eyebrow">Comece como cliente</span>
          <h2>Crie sua conta para comprar e acompanhar seus equipamentos.</h2>
        </div>
        <Link to="/cadastro">Criar conta agora</Link>
      </section>

      <footer className="public-footer">
        <strong>TechRepair Hub</strong>
        <span>Assistência técnica, produtos revisados e acompanhamento em tempo real.</span>
      </footer>
    </main>
  );
}
