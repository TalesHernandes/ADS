import React from "react";

const DependentesSelect = ({ dependentes, onChange }) => {
    return (
        <div className="seletor-dependentes">
            <label htmlFor="dependentes" className="labelDependentes">Dependentes Cadastrados: </label>
            <select id="dependentes" onChange={(e) => onChange(dependentes.find(d => d.id === parseInt(e.target.value)))}>
                <option value="">*Admin* Selecione ou Crie Novo Dependente</option>
                {dependentes.map(dependente => (
                    <option key={dependente.id} value={dependente.id}>
                        Nome: {dependente.nome}
                    </option>
                ))}
            </select>
        </div>
    );
};

export default DependentesSelect;
