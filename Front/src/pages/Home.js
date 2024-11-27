import "../styles.css";
import logoStar from "../imgs/Star.png";
import notifica from "../imgs/Bell.png";
import userIcon from "../imgs/UserIcon.png";
import { Link } from "react-router-dom";

export default function Home() {
  return (
    <div className="pagina">
      <style>
        @import
        url('https://fonts.googleapis.com/css2?family=Agdasima:wght@700&display=swap');
      </style>
      <header className="cabecalho">
        <div className="logo">
          <img src={logoStar} alt="Star Tech" />
          <h1>Tech - Controle de Contas</h1>
        </div>
        <div className="informacoes-topo">
          <div className="configuracoes">
            <p className="pagina-inicial">Pagina Inicial</p>
            <button className="aumentar-fonte">A+</button>
          </div>
        </div>
      </header>

      <main className="conteudo-principal">
        <div className="opcoes-conta">
          <div className="conta-corrente conta-selecionada">
            <h2>Conta Corrente</h2>
          </div>

          <div className="conta-poupanca">
            <h2>Conta Poupança</h2>
          </div>

          <div className="carteira-fisica">
            <h2>Carteira Física</h2>
          </div>
        </div>

        <div className="configuracoes-conta">
          <button className="notificacoes">
            <img src={notifica} alt="Notificações" />
            <p> Notificações </p>
          </button>
          <Link to="/Cadastrar" className="configurar-contas">
            <img src={userIcon} alt="UserIcon" />
            <p> Configuração de Contas </p>
          </Link>

        </div>

        <div className="seletor-conta">
          <label for="contas">Selecionar Conta:</label>
          <select id="contas">
            <option value="conta-corrente-1">Conta Corrente 1</option>
            <option value="conta-corrente-2">Conta Corrente 2</option>
          </select>
        </div>

        <div className="area-saudacao">
          <p>Olá UserAdmin! </p>
          <p>O que gostaria de realizar?</p>
          <div className="info-saldo">
            <p>
              Saldo Disponível: <strong className="saldo">2.553,00 R$</strong>
            </p>
            <p className="limite-conta">
              Limite de Conta: <strong className="limite">1.000,00 R$</strong>
            </p>
            <a href="#" className="extrato">
              Extrato
            </a>
          </div>
        </div>

        <div className="acoes">
          <button className="botao transferir"><Link to="/Transferencia">Transferir</Link></button>
          <button className="botao sacar"><Link to="/Sacar">Sacar</Link></button>
          <button className="botao depositar"><Link to="/Depositar">Depositar</Link></button>
        </div>
      </main>
    </div>
  );
}
