import React, { useEffect, useState } from 'react';
import axios from 'axios';
import '../Extrato.css';

function Extrato() {
    const [extrato, setExtrato] = useState([]);

    useEffect(() => {
        const fetchExtrato = async () => {
            try {
                const response = await axios.get('https://1tne0li606.execute-api.us-east-1.amazonaws.com/extrato/extrato');
                setExtrato(response.data);
            } catch (error) {
                console.error('Erro ao buscar extrato:', error);
            }
        };

        fetchExtrato();
    }, []);

    return (
        <div className="extrato-container">
            <a href="/">Página Inicial</a>
            <h1 className="extrato-title">Extrato</h1>
            <ul className="extrato-list">
                {extrato.map((item) => (
                    <li key={item.id} className="extrato-item">
                        <p>Data: {item.data}</p>
                        <p>Tipo de Operação: {item.tipoOperacao}</p>
                        <p>Valor: {item.valor}</p>
                        {item.tipoOperacao === 'Transferência' ? (
                            <p>Conta Início: {item.contaInicio}</p>
                        ) : (
                            <p>Conta: {item.contaInicio}</p>
                        )}
                        {item.tipoOperacao === 'Transferência' && (
                            <p>Conta Fim: {item.contafim}</p>
                        )}
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default Extrato;