import React, { useState } from 'react';
import "../styles.css";

function AlterarLimite() {
  const [idConta, setIdConta] = useState('');
  const [novoLimite, setNovoLimite] = useState('');
  const [mensagem, setMensagem] = useState('');
  const [erro, setErro] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Validação simples dos campos
    if (!idConta || !novoLimite || parseFloat(novoLimite) <= 0) {
      setErro('Por favor, preencha todos os campos corretamente!');
      return;
    }

    setErro('');  // Limpa a mensagem de erro

    try {
      // Construindo a URL com os parâmetros da conta e novo limite
      const url = `http://54.156.4.81:8080/limite/${idConta}/${novoLimite}`;

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
      console.error('Erro ao alterar o limite:', error);
      setErro('Ocorreu um erro ao alterar o limite. Tente novamente.');
    }
  };

  return (
    <div className="limite-container">
        <a href="/">Página Inicial</a>
      <h2>Alterar Limite da Conta</h2>

      {mensagem && <div className="mensagem sucesso">{mensagem}</div>}
      {erro && <div className="mensagem erro">{erro}</div>}

      <form onSubmit={handleSubmit} className="limite-form">
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
          <label htmlFor="novoLimite">Novo Limite:</label>
          <input
            type="number"
            id="novoLimite"
            value={novoLimite}
            onChange={(e) => setNovoLimite(e.target.value)}
            placeholder="Novo limite da conta"
            min="0.01"
            step="0.01"
          />
        </div>

        <button type="submit" className="btn-submit">Alterar Limite</button>
      </form>
    </div>
  );
}

export default AlterarLimite;
