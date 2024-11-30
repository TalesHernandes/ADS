import React, { useState } from 'react';
import "../styles.css";

function Transferencia() {
  const [contaOrigem, setContaOrigem] = useState('');
  const [contaDestino, setContaDestino] = useState('');
  const [valor, setValor] = useState('');
  const [mensagem, setMensagem] = useState('');
  const [erro, setErro] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Validação simples dos campos
    if (!contaOrigem || !contaDestino || !valor || parseFloat(valor) <= 0) {
      setErro('Por favor, preencha todos os campos corretamente!');
      return;
    }

    setErro('');  // Limpa a mensagem de erro

    try {
      // Construindo a URL com os parâmetros da conta e valor
      const url = `http://54.156.4.81:8080/api/contas/transferir/${contaOrigem}/${contaDestino}/${valor}`;

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
      console.error('Erro ao realizar a transferência:', error);
      setErro('Ocorreu um erro ao realizar a transferência. Tente novamente.');
    }
  };

  return (
    <div className="transferencia-container">

        {/* <a href="/">Página Inicial</a> */}
      <h2>Transferência Bancária</h2>
      
      {mensagem && <div className="mensagem sucesso">{mensagem}</div>}
      {erro && <div className="mensagem erro">{erro}</div>}

      <form onSubmit={handleSubmit} className="transferencia-form">
        <div className="campo">
          <label htmlFor="contaOrigem">Conta de Origem:</label>
          <input
            type="number"
            id="contaOrigem"
            value={contaOrigem}
            onChange={(e) => setContaOrigem(e.target.value)}
            placeholder="Número da conta de origem"
          />
        </div>

        <div className="campo">
          <label htmlFor="contaDestino">Conta de Destino:</label>
          <input
            type="number"
            id="contaDestino"
            value={contaDestino}
            onChange={(e) => setContaDestino(e.target.value)}
            placeholder="Número da conta de destino"
          />
        </div>

        <div className="campo">
          <label htmlFor="valor">Valor:</label>
          <input
            type="number"
            id="valor"
            value={valor}
            onChange={(e) => setValor(e.target.value)}
            placeholder="Valor da transferência"
            min="0.01"
            step="0.01"
          />
        </div>

        <button type="submit" className="btn-submit">Transferir</button>
      </form>
    </div>
  );
}

export default Transferencia;
