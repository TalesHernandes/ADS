import React, { useState } from 'react';
import "../styles.css";

function Deposito() {
  const [idConta, setIdConta] = useState('');
  const [valor, setValor] = useState('');
  const [mensagem, setMensagem] = useState('');
  const [erro, setErro] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Validação simples dos campos
    if (!idConta || !valor || parseFloat(valor) <= 0) {
      setErro('Por favor, preencha todos os campos corretamente!');
      return;
    }

    setErro('');  // Limpa a mensagem de erro

    try {
      // Construindo a URL com os parâmetros da conta e valor
      const url = `http://54.156.4.81:8080/api/contas/depositar/${idConta}/${valor}`;

      // Fazendo a requisição PUT para a API
      const response = await fetch(url, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
      });

      // Verifica se a resposta é bem-sucedida
      if (response.ok) {
        const data = await response.json();
        setMensagem(data.mensagem); // Exibe a mensagem de sucesso
      } else {
        const errorData = await response.json();
        setErro(errorData.mensagem); // Exibe a mensagem de erro
      }
    } catch (error) {
      console.error('Erro ao realizar o depósito:', error);
      setErro('Ocorreu um erro ao realizar o depósito. Tente novamente.');
    }
  };

  return (
    <div className="deposito-container">
        <a href="/">Página Inicial</a>
      <h2>Depósito Bancário</h2>

      {mensagem && <div className="mensagem sucesso">{mensagem}</div>}
      {erro && <div className="mensagem erro">{erro}</div>}

      <form onSubmit={handleSubmit} className="deposito-form">
        <div className="campo">
          <label htmlFor="idConta">ID da Conta:</label>
          <input
            type="number"
            id="idConta"
            value={idConta}
            onChange={(e) => setIdConta(e.target.value)}
            placeholder="Número da conta"
          />
        </div>

        <div className="campo">
          <label htmlFor="valor">Valor:</label>
          <input
            type="number"
            id="valor"
            value={valor}
            onChange={(e) => setValor(e.target.value)}
            placeholder="Valor a ser depositado"
            min="0.01"
            step="0.01"
          />
        </div>

        <button type="submit" className="btn-submit">Depositar</button>
      </form>
    </div>
  );
}

export default Deposito;
