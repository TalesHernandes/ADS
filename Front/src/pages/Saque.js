import React, { useState } from 'react';
import "../styles.css";

function Saque() {
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
      const url = `http://54.156.4.81:8080/api/contas/sacar/${idConta}/${valor}`;

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
      console.error('Erro ao realizar o saque:', error);
      setErro('Ocorreu um erro ao realizar o saque. Tente novamente.');
    }
  };

  return (
    <div className="saque-container">
        <a href="/">Página Inicial</a>
      <h2>Saque Bancário</h2>

      {mensagem && <div className="mensagem sucesso">{mensagem}</div>}
      {erro && <div className="mensagem erro">{erro}</div>}

      <form onSubmit={handleSubmit} className="saque-form">
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
            placeholder="Valor a ser sacado"
            min="0.01"
            step="0.01"
          />
        </div>

        <button type="submit" className="btn-submit">Sacar</button>
      </form>
    </div>
  );
}

export default Saque;
