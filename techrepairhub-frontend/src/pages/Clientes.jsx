import { useEffect, useState } from "react";
import api from "../api/axiosConfig";

export default function Clientes() {
  const [clientes, setClientes] = useState([]);
  const [erro, setErro] = useState("");
  const [sucesso, setSucesso] = useState("");
  const [carregando, setCarregando] = useState(false);

  const [form, setForm] = useState({
    nome: "",
    email: "",
    senha: "",
    cpf: "",
    telefone: "",
    whatsapp: "",
    endereco: "",
  });

  useEffect(() => {
    carregarClientes();
  }, []);

  async function carregarClientes() {
    try {
      const response = await api.get("/clientes");
      console.log("Clientes vindos do back:", response.data);
      setClientes(response.data);
    } catch (error) {
      console.log("Erro ao carregar clientes:", error);
      setErro("Erro ao carregar clientes.");
    }
  }

  function handleChange(event) {
    const { name, value } = event.target;

    setForm({
      ...form,
      [name]: value,
    });
  }

  async function cadastrarCliente(event) {
    event.preventDefault();

    setErro("");
    setSucesso("");
    setCarregando(true);

    try {
      const usuarioParaEnviar = {
        nome: form.nome,
        email: form.email,
        senha: form.senha,
        perfil: "CLIENTE",
      };

      console.log("Criando usuário:", usuarioParaEnviar);

      const usuarioResponse = await api.post("/usuarios", usuarioParaEnviar);

      console.log("Usuário criado:", usuarioResponse.data);

      const usuarioId =
        usuarioResponse.data.id ||
        usuarioResponse.data.usuarioId ||
        usuarioResponse.data.codigo;

      if (!usuarioId) {
        throw new Error("O back-end não retornou o ID do usuário criado.");
      }

      const clienteParaEnviar = {
        usuarioId: usuarioId,
        cpf: form.cpf,
        telefone: form.telefone,
        whatsapp: form.whatsapp,
        endereco: form.endereco,
      };

      console.log("Criando cliente:", clienteParaEnviar);

      await api.post("/clientes", clienteParaEnviar);

      setSucesso("Cliente cadastrado com sucesso.");

      setForm({
        nome: "",
        email: "",
        senha: "",
        cpf: "",
        telefone: "",
        whatsapp: "",
        endereco: "",
      });

      carregarClientes();
    } catch (error) {
      console.log("Erro completo:", error);
      console.log("Resposta do back:", error.response?.data);
      console.log("Status:", error.response?.status);

      const resposta = error.response?.data;

      const mensagem =
        resposta?.message ||
        resposta?.erro ||
        resposta?.error ||
        resposta?.mensagem ||
        JSON.stringify(resposta) ||
        error.message ||
        "Erro ao cadastrar cliente. Verifique os dados.";

      setErro(mensagem);
    } finally {
      setCarregando(false);
    }
  }

  function pegarNome(cliente) {
    return (
      cliente.nome ||
      cliente.nomeCliente ||
      cliente.usuario?.nome ||
      cliente.usuarioNome ||
      "-"
    );
  }

  function pegarEmail(cliente) {
    return (
      cliente.email ||
      cliente.emailCliente ||
      cliente.usuario?.email ||
      cliente.usuarioEmail ||
      "-"
    );
  }

  function pegarCpf(cliente) {
    return cliente.cpf || cliente.cpfCnpj || "-";
  }

  function pegarWhatsapp(cliente) {
    return cliente.whatsapp || "-";
  }

  function pegarEndereco(cliente) {
    return cliente.endereco || "-";
  }

  function pegarUsuarioId(cliente) {
    return cliente.usuarioId || cliente.usuario?.id || "-";
  }

  return (
    <div>
      <div className="page-header">
        <div>
          <h1>Clientes</h1>
          <p>Cadastro e consulta de clientes do sistema.</p>
        </div>
      </div>

      {erro && <div className="alerta-erro">{erro}</div>}
      {sucesso && <div className="alerta-sucesso">{sucesso}</div>}

      <div className="content-grid">
        <section className="form-card">
          <h2>Novo cliente</h2>

          <form onSubmit={cadastrarCliente}>
            <label>Nome</label>
            <input
              type="text"
              name="nome"
              value={form.nome}
              onChange={handleChange}
              placeholder="Nome do cliente"
              required
            />

            <label>E-mail</label>
            <input
              type="email"
              name="email"
              value={form.email}
              onChange={handleChange}
              placeholder="cliente@email.com"
              required
            />

            <label>Senha</label>
            <input
              type="password"
              name="senha"
              value={form.senha}
              onChange={handleChange}
              placeholder="Senha inicial do cliente"
              required
            />

            <label>CPF</label>
            <input
              type="text"
              name="cpf"
              value={form.cpf}
              onChange={handleChange}
              placeholder="Digite o CPF"
              required
            />

            <label>Telefone</label>
            <input
              type="text"
              name="telefone"
              value={form.telefone}
              onChange={handleChange}
              placeholder="(31) 99999-9999"
              required
            />

            <label>WhatsApp</label>
            <input
              type="text"
              name="whatsapp"
              value={form.whatsapp}
              onChange={handleChange}
              placeholder="(31) 99999-9999"
              required
            />

            <label>Endereço</label>
            <input
              type="text"
              name="endereco"
              value={form.endereco}
              onChange={handleChange}
              placeholder="Rua, número, bairro, cidade"
              required
            />

            <button type="submit" disabled={carregando}>
              {carregando ? "Salvando..." : "Cadastrar cliente"}
            </button>
          </form>
        </section>

        <section className="table-card">
          <h2>Clientes cadastrados</h2>

          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Usuário ID</th>
                <th>Nome</th>
                <th>E-mail</th>
                <th>CPF</th>
                <th>Telefone</th>
                <th>WhatsApp</th>
                <th>Endereço</th>
                <th>Status</th>
              </tr>
            </thead>

            <tbody>
              {clientes.length === 0 ? (
                <tr>
                  <td colSpan="9">Nenhum cliente cadastrado.</td>
                </tr>
              ) : (
                clientes.map((cliente) => (
                  <tr key={cliente.id}>
                    <td>{cliente.id}</td>
                    <td>{pegarUsuarioId(cliente)}</td>
                    <td>{pegarNome(cliente)}</td>
                    <td>{pegarEmail(cliente)}</td>
                    <td>{pegarCpf(cliente)}</td>
                    <td>{cliente.telefone || "-"}</td>
                    <td>{pegarWhatsapp(cliente)}</td>
                    <td>{pegarEndereco(cliente)}</td>
                    <td>
                      <span
                        className={
                          cliente.ativo === false
                            ? "status-inativo"
                            : "status-ativo"
                        }
                      >
                        {cliente.ativo === false ? "Inativo" : "Ativo"}
                      </span>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </section>
      </div>
    </div>
  );
}