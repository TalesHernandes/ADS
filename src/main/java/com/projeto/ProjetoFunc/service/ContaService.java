package com.projeto.ProjetoFunc.service;

import com.projeto.ProjetoFunc.model.Conta;
import com.projeto.ProjetoFunc.model.Pessoa;
import com.projeto.ProjetoFunc.repository.ContaRepository;
import com.projeto.ProjetoFunc.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContaService {
    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    public List<Conta> cadastrarContas(Long pessoaId, List<Conta> contas) {
        List<Conta> contasCriadas = new ArrayList<>();
        // Busca a pessoa associada pelo ID
        Pessoa pessoa = pessoaRepository.findById(pessoaId).orElse(null);

        if (pessoa != null) {
            for (Conta conta : contas) {
                conta.setPessoa(pessoa); // Associa a conta à pessoa
                contasCriadas.add(contaRepository.save(conta)); // Salva a conta
            }
        }
        return contasCriadas;
    }

    public List<Conta> listarContas() {
        return contaRepository.findAll();
    }

    public Conta obterContaPorId(Long id) {
        return contaRepository.findById(id).orElse(null);
    }

    public List<Conta> listarContasPorPessoa(Long pessoaId) {
        return contaRepository.findByPessoaId(pessoaId);
    }

}
