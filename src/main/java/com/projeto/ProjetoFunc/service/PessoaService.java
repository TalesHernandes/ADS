package com.projeto.ProjetoFunc.service;

import com.projeto.ProjetoFunc.model.Pessoa;
import com.projeto.ProjetoFunc.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PessoaService {
    @Autowired
    private PessoaRepository pessoaRepository;

    public Pessoa cadastrarPessoa(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public List<Pessoa> listarPessoas() {
        return pessoaRepository.findAll();
    }

    public Pessoa obterPessoaPorId(Long id) {
        return pessoaRepository.findById(id).orElse(null);
    }
}
