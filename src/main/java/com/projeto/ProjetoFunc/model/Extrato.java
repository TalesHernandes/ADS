package com.projeto.ProjetoFunc.model;

import jakarta.persistence.*;

/**
 -- TABELA EXTRATO PARA PROJETO
 CREATE TABLE Extrato (
 id INT AUTO_INCREMENT PRIMARY KEY,
 containicio INT,  -- CONTA ORIGEM
 contafim INT,     -- CONTA DESTINO
 valor DECIMAL(10, 2) NOT NULL,
 data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 FOREIGN KEY (containicio) REFERENCES Conta(id),
 FOREIGN KEY (contafim) REFERENCES Conta(id)
 );
 */

@Entity
@Table(name = "Extrato")
public class Extrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer containicio;
    private Integer contafim;
    private double valor;
    private String data;
    private String tipoOperacao;

    public Extrato() {
    }

    public Extrato(Long id, Integer contaInicio, Integer contaFim, double valor, String data, String tipoOperacao) {
        this.id = id;
        this.containicio = contaInicio;
        this.contafim = contaFim;
        this.valor = valor;
        this.data = data;
        this.tipoOperacao = tipoOperacao;
    }

    public Long getId() {
        return id;
    }

    public Integer getContaInicio() {
        return containicio;
    }

    public Integer getContafim() {
        return contafim;
    }

    public double getValor() {
        return valor;
    }

    public String getData() {
        return data;
    }

    public String getTipoOperacao() {
        return tipoOperacao;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setContaInicio(Integer contaInicio) {
        this.containicio = contaInicio;
    }

    public void setContafim(Integer contafim) {
        this.contafim = contafim;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setTipoOperacao(String tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"containicio\":" + containicio +
                ", \"contafim\":" + contafim +
                ", \"valor\":" + valor +
                ", \"data\":\"" + data + "\"" +
                ", \"tipoOperacao\":\"" + tipoOperacao + "\"" +
                "}";
    }
}
