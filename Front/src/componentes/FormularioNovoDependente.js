import React, { useState } from 'react';

const FormularioNovoDependente = ({ onAddDependente }) => {
    const [nome, setNome] = useState('');
    const [grauParentesco, setGrauParentesco] = useState('');
    const [tiposConta, setTiposConta] = useState({
        contaCorrente: false,
        contaPoupanca: false,
        carteiraFisica: false,
    });

    const handleCheckboxChange = (e) => {
        setTiposConta({
            ...tiposConta,
            [e.target.value]: e.target.checked,
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        const contas = Object.keys(tiposConta).filter(tipo => tiposConta[tipo]).map(tipo => {
            return {
                tipoConta: tipo === 'contaCorrente' ? 'Corrente' : tipo === 'contaPoupanca' ? 'Poupança' : 'Carteira Física',
                saldo: 0,
                limite: 0  
            };
        });

        const novoDependente = {
            nome,
            parentesco: grauParentesco,
            admin: false,
            contas,
        };
        console.log('Novo dependente enviado:', novoDependente);
        onAddDependente(novoDependente); 

        // LIMPAR FORMULARIOS
        setNome('');
        setGrauParentesco('');
        setTiposConta({
            contaCorrente: false,
            contaPoupanca: false,
            carteiraFisica: false,
        });
    };

    return (
        <form className="formulario-dependente" onSubmit={handleSubmit}>
            <label htmlFor="nome">Nome:</label>
            <input type="text" id="nome" name="nome" placeholder="Nome do dependente" value={nome} onChange={(e) => setNome(e.target.value)} required />

            <label htmlFor="grauParentesco">Grau de Parentesco:</label>
            <input type="text" id="grauParentesco" name="grauParentesco" placeholder="Ex: Filho, Esposa" value={grauParentesco} onChange={(e) => setGrauParentesco(e.target.value)} required />

            <div className="selecionar-contas">
                <label htmlFor="tipoConta">Tipo(s) de Conta:</label>

                <div className="opcoes-conta">
                    <div className="opcao">
                        <input type="checkbox" id="contaCorrente" name="tipoConta" value="contaCorrente" checked={tiposConta.contaCorrente} onChange={handleCheckboxChange} />
                        <label htmlFor="contaCorrente">Conta Corrente</label>
                    </div>

                    <div className="opcao">
                        <input type="checkbox" id="contaPoupanca" name="tipoConta" value="contaPoupanca" checked={tiposConta.contaPoupanca} onChange={handleCheckboxChange} />
                        <label htmlFor="contaPoupanca">Conta Poupança</label>
                    </div>

                    <div className="opcao">
                        <input type="checkbox" id="carteiraFisica" name="tipoConta" value="carteiraFisica" checked={tiposConta.carteiraFisica} onChange={handleCheckboxChange} />
                        <label htmlFor="carteiraFisica">Carteira Física</label>
                    </div>
                </div>
            </div>
            <button type="submit" className="botao-submit">Adicionar Dependente</button>
        </form>
    );
};

export default FormularioNovoDependente;
