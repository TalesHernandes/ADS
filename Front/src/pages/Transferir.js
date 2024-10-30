import "../styles.css";
import logoStar from "../imgs/Star.png";
import userIcon from "../imgs/UserIcon.png";
import nova from "../imgs/nova.png"
import { Link } from "react-router-dom";
import React, { useState, useEffect } from "react";
import axios from 'axios';
import DependentesSelect from "../componentes/DependentesSelect"; 
import FormularioNovoDependente from "../componentes/FormularioNovoDependente"; 


// CÓDIGO PRINCIPAL 

export default function Transferir() {
    const [pessoa, setPessoa] = useState(null);
    const [contas, setContas] = useState([]);
    const [dependentes, setDependentes] = useState([]);
    const [dependenteSelecionado, setDependenteSelecionado] = useState(null);
    const [isAdmin, setIsAdmin] = useState(false);
    const [numeroContaSelecionada, setNumeroContaSelecionada] = useState(null);

    // FUNÇÃO CARREGAR DADOS
    useEffect(() => {
      
      const fetchPessoa = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/pessoas/4');
            setPessoa(response.data);
            setIsAdmin(response.data.admin);
        } catch (error) {
            console.error('Erro ao buscar dados do admin:', error);
        }
    };
  
      fetchPessoa();
    }, []);
    // FUNÇÃO CARREGAR LISTA DE DEPENDENTES
    useEffect(() => {
      
      const fetchDependentes = async () => {
          try {
              const response = await axios.get('http://localhost:8080/api/pessoas');
              const dependentesFiltrados = response.data.filter(pessoa => !pessoa.admin); // Filtrando apenas dependentes
              setDependentes(dependentesFiltrados);
          } catch (error) {
              console.error('Erro ao buscar dependentes:', error);
          }
      };

      fetchDependentes();
  }, []);

  useEffect(() => {
    if (dependenteSelecionado) {
        setContas(dependenteSelecionado.contas || []);
    } else if (pessoa) {
        setContas(pessoa.contas || []);
    }
  }, [dependenteSelecionado, pessoa]);
  

    // FUNÇÃO PARA MUDAR DADOS DA PAGINA QUANDO O DEPENDENTE É SELECIONADO
    const handleDependente = (dependente) => {
      setDependenteSelecionado(dependente);
    };
    
    // FUNÇÃO PARA NOVO DEPENDENTE NO FORMULARIO
    const handleAddDependente = async (novoDependente) => {
      try {
          // Envia os dados do novo dependente e as contas em uma única requisição
          const response = await axios.post('http://localhost:8080/api/pessoas', novoDependente);
          console.log('Resposta da API:', response.data);
          
          // Adiciona o novo dependente à lista de dependentes
          setDependentes([...dependentes, response.data]);
          alert("Dependente adicionado com sucesso!");
      } catch (error) {
          console.error('Erro ao adicionar dependente:', error.response ? error.response.data : error.message);
      }
    };
  

    // FUNÇÃO PARA ADICIONAR NOVA CONTA
    const handleAdicionarConta = async (tipoConta) => {
      const pessoaId = dependenteSelecionado ? dependenteSelecionado.id : pessoa.id;
  
      const novaConta = {
          tipoConta: tipoConta,
          saldo: 0,
          limite: 0,
          pessoa_id: pessoaId,
      };
  
      try {
          const response = await axios.post(`http://localhost:8080/api/contas/${pessoaId}`, [novaConta]);
          console.log('Nova conta criada:', response.data);
  
          if (dependenteSelecionado) {
              const contasAtualizadas = [...(dependenteSelecionado.contas || []), response.data];
              setDependenteSelecionado({ ...dependenteSelecionado, contas: contasAtualizadas });
              setContas(contasAtualizadas);
          } else {
              const contasAtualizadas = [...(pessoa.contas || []), response.data];
              setPessoa({ ...pessoa, contas: contasAtualizadas });
              setContas(contasAtualizadas);
          }
          setNumeroContaSelecionada(response.data.id);  // Define o número da conta após a criação
          alert(`Conta ${tipoConta} adicionada com sucesso!`);
      } catch (error) {
          console.error('Erro ao adicionar nova conta:', error);
          alert('Erro ao adicionar nova conta.');
      }
  };


  // PAGINA TRANSFERIR /////////////////////////////

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
            <Link to="/" className="pagina-inicial">Pagina Inicial</Link>
            <button className="aumentar-fonte">A+</button>
          </div>
        </div>
      </header>

      <main className="conteudo-principal">
        <section className="blocoMaior">
          <div className="blocoSuperior">
            <div className="blocoSuperiorEsquerda">
              <img src={userIcon}></img>
              <p className="bseUserAdmin">{dependenteSelecionado ? dependenteSelecionado.parentesco : pessoa?.parentesco}</p>
            </div>
            <div className="blocoSuperiorDireita">
              <p className="nConta">    
                Conta N°{numeroContaSelecionada || 
                  (dependenteSelecionado && dependenteSelecionado.contas && dependenteSelecionado.contas.length > 0 
                  ? dependenteSelecionado.contas[0].numero 
                  : (pessoa && pessoa.contas && pessoa.contas.length > 0 
                    ? pessoa.contas[0].numero 
                    : "Nenhuma conta disponível"))}
              </p>
            </div>
          </div>
          <div className="blocoInferior">
            <div className="blocoInferiorDireita">
              <p className="nomeCompleto">{dependenteSelecionado ? dependenteSelecionado.nome : pessoa?.nome}</p>
            </div>
            <div className="blocoInferiorEsquerda">
              <div className="divTipoConta">
                <p className="tipoConta">Selecione a Conta Desejada:</p>
                <div className="contasEncontradas">
                  {(dependenteSelecionado ? dependenteSelecionado.contas : pessoa?.contas)?.map(conta => (
                    <div key={conta.id} onClick={() => setNumeroContaSelecionada(conta.id)}>
                      <p>{conta.tipoConta || "Tipo não especificado"} N°{conta.id || "ID não encontrado"}</p>
                    </div>
                  ))}
                </div>
              </div>
              <div className="adicionarConta">
                <div className="divContaCorrente" onClick={() => handleAdicionarConta('Corrente')}>
                  <p className="pContacorrente">Conta Corrente</p>
                  <img src={nova}></img>
                  <p className="Nova">Nova</p>
                </div>
                <div className="divContaPoupanca" onClick={() => handleAdicionarConta('Poupança')}>
                  <p className="pContaPoupanca">Conta Poupança</p>
                  <img src={nova}></img>
                  <p className="Nova">Nova</p>
                </div>
                <div className="divCarteira" onClick={() => handleAdicionarConta('Carteira Física')}>
                  <p className="pCarteira">Carteira Física</p>
                  <img src={nova}></img>
                  <p className="Nova">Nova</p>
                </div>
              </div>
            </div>
          </div>
        </section>
        <section className="parteInferior">
          <div className="parteInferiorEsquerda">
            <DependentesSelect dependentes={dependentes} onChange={handleDependente}/>
          </div>
          <div className="parteInferiorDireita">
            
            {isAdmin && !dependenteSelecionado && (
              <>
              <p className="novoDependente"  >Novo Dependente</p>
              <FormularioNovoDependente onAddDependente={handleAddDependente} />
              </>
            )}
          </div>
        </section>
      </main>
    </div>
    );
  }