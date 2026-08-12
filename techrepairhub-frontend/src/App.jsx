import { BrowserRouter, Route, Routes } from "react-router-dom";
import { AuthProvider } from "./context/AuthContext";
import ProtectedRoute from "./components/ProtectedRoute";
import HomeRedirect from "./components/HomeRedirect";
import LayoutInterno from "./layouts/LayoutInterno";
import LayoutPublico from "./layouts/LayoutPublico";
import LayoutCliente from "./layouts/LayoutCliente";
import LayoutTecnico from "./layouts/LayoutTecnico";
import Home from "./pages/public/Home";
import Catalogo from "./pages/public/Catalogo";
import ProdutoDetalhe from "./pages/public/ProdutoDetalhe";
import Login from "./pages/Login";
import CadastroCliente from "./pages/CadastroCliente";
import Dashboard from "./pages/Dashboard";
import Clientes from "./pages/Clientes";
import Usuarios from "./pages/Usuarios";
import Tecnicos from "./pages/Tecnicos";
import Categorias from "./pages/Categorias";
import Produtos from "./pages/Produtos";
import Estoque from "./pages/Estoque";
import MovimentacoesEstoque from "./pages/MovimentacoesEstoque";
import Pedidos from "./pages/Pedidos";
import Carrinho from "./pages/Carrinho";
import Garantias from "./pages/Garantias";
import Equipamentos from "./pages/Equipamentos";
import OrdensServico from "./pages/OrdensServico";
import OrdemServicoDetalhe from "./pages/OrdemServicoDetalhe";
import Orcamentos from "./pages/Orcamentos";
import Pecas from "./pages/Pecas";
import PecasUtilizadas from "./pages/PecasUtilizadas";
import RelatoriosTecnicos from "./pages/RelatoriosTecnicos";
import RelatoriosInternos from "./pages/RelatoriosInternos";
import Financeiro from "./pages/Financeiro";
import Receitas from "./pages/financeiro/Receitas";
import Despesas from "./pages/financeiro/Despesas";
import FluxoCaixa from "./pages/financeiro/FluxoCaixa";
import Permissoes from "./pages/Permissoes";
import AreaTecnico from "./pages/tecnico/AreaTecnico";
import MinhasOrdensTecnico from "./pages/tecnico/MinhasOrdensServico";
import RelatoriosTecnico from "./pages/tecnico/RelatoriosTecnico";
import AreaCliente from "./pages/cliente/AreaCliente";
import MinhasOrdensCliente from "./pages/cliente/MinhasOrdensServico";
import MeusEquipamentos from "./pages/cliente/MeusEquipamentos";
import MeuCarrinho from "./pages/cliente/MeuCarrinho";

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route element={<LayoutPublico />}>
            <Route path="/" element={<Home />} />
            <Route path="/catalogo" element={<Catalogo />} />
            <Route path="/catalogo/produto/:id" element={<ProdutoDetalhe />} />
            <Route path="/login" element={<Login />} />
            <Route path="/cadastro" element={<CadastroCliente />} />
          </Route>

          <Route
            path="/"
            element={
              <ProtectedRoute perfis={["ADMIN", "GESTOR", "ATENDENTE", "TECNICO"]}>
                <LayoutInterno />
              </ProtectedRoute>
            }
          >
            <Route path="dashboard" element={<Dashboard />} />
            <Route path="clientes" element={<Clientes />} />
            <Route path="usuarios" element={<Usuarios />} />
            <Route path="tecnicos" element={<Tecnicos />} />
            <Route path="categorias" element={<Categorias />} />
            <Route path="produtos" element={<Produtos />} />
            <Route path="estoque" element={<Estoque />} />
            <Route path="movimentacoes-estoque" element={<MovimentacoesEstoque />} />
            <Route path="pedidos" element={<Pedidos />} />
            <Route
              path="carrinho"
              element={
                <ProtectedRoute perfis={["ADMIN", "GESTOR"]}>
                  <Carrinho />
                </ProtectedRoute>
              }
            />
            <Route path="garantias" element={<Garantias />} />
            <Route path="equipamentos" element={<Equipamentos />} />
            <Route path="ordens-servico" element={<OrdensServico />} />
            <Route path="ordens-servico/:id" element={<OrdemServicoDetalhe />} />
            <Route path="orcamentos" element={<Orcamentos />} />
            <Route path="pecas" element={<Pecas />} />
            <Route path="pecas-utilizadas" element={<PecasUtilizadas />} />
            <Route
              path="relatorios-tecnicos"
              element={
                <ProtectedRoute perfis={["ADMIN"]}>
                  <RelatoriosTecnicos />
                </ProtectedRoute>
              }
            />
            <Route
              path="relatorios"
              element={
                <ProtectedRoute perfis={["ADMIN"]}>
                  <RelatoriosInternos />
                </ProtectedRoute>
              }
            />
            <Route path="financeiro" element={<Financeiro />} />
            <Route path="financeiro/receitas" element={<Receitas />} />
            <Route path="financeiro/despesas" element={<Despesas />} />
            <Route path="financeiro/fluxo-caixa" element={<FluxoCaixa />} />
            <Route
              path="permissoes"
              element={
                <ProtectedRoute perfis={["ADMIN", "GESTOR", "TECNICO"]}>
                  <Permissoes />
                </ProtectedRoute>
              }
            />
            <Route path="*" element={<HomeRedirect />} />
          </Route>

          <Route
            path="/tecnico"
            element={
              <ProtectedRoute perfis={["ADMIN", "TECNICO"]}>
                <LayoutTecnico />
              </ProtectedRoute>
            }
          >
            <Route index element={<AreaTecnico />} />
            <Route path="minhas-os" element={<MinhasOrdensTecnico />} />
            <Route path="minhas-os/:id" element={<OrdemServicoDetalhe />} />
            <Route path="relatorios" element={<RelatoriosTecnico />} />
          </Route>

          <Route
            path="/cliente"
            element={
              <ProtectedRoute perfis={["CLIENTE"]}>
                <LayoutCliente />
              </ProtectedRoute>
            }
          >
            <Route index element={<AreaCliente />} />
            <Route path="ordens-servico" element={<MinhasOrdensCliente />} />
            <Route path="equipamentos" element={<MeusEquipamentos />} />
            <Route path="carrinho" element={<MeuCarrinho />} />
          </Route>

          <Route
            path="/"
            element={
              <ProtectedRoute>
                <LayoutInterno />
              </ProtectedRoute>
            }
          >
            <Route path="*" element={<HomeRedirect />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;
