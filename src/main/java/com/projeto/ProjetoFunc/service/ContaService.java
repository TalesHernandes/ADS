package com.projeto.ProjetoFunc.service;

import com.projeto.ProjetoFunc.model.Conta;
import com.projeto.ProjetoFunc.model.Pessoa;
import com.projeto.ProjetoFunc.repository.ContaRepository;
import com.projeto.ProjetoFunc.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ContaService {
    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    public Conta cadastrarConta(Long pessoaId, Conta conta) throws Exception {
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new Exception("Pessoa não encontrada"));
        conta.setPessoa(pessoa);

        if (!"Corrente".equals(conta.getTipoConta()) && conta.getLimite().compareTo(BigDecimal.ZERO) > 0) {
            throw new IllegalArgumentException("Somente contas Corrente podem ter limite maior que 0.");
        }

        return contaRepository.save(conta);
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
