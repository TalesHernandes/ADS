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
        Pessoa pessoa = pessoaRepository.findById(pessoaId).orElse(null);

        if (pessoa != null) {
            for (Conta conta : contas) {
                conta.setPessoa(pessoa);
                contasCriadas.add(contaRepository.save(conta));
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

    public Conta transferir(Long idContaOrigem, Long idContaDestino, Double valor) {
        Conta contaOrigem = contaRepository.findById(idContaOrigem).orElse(null);
        Conta contaDestino = contaRepository.findById(idContaDestino).orElse(null);

        if (idContaDestino.equals(idContaOrigem)) {
            throw new IllegalArgumentException("Conta de origem e destino não podem ser iguais");
        }

        if (contaOrigem == null) {
            throw new IllegalArgumentException("Conta de origem não encontrada");
        }

        if (contaDestino == null) {
            throw new IllegalArgumentException("Conta de destino não encontrada");
        }

        double saldoDisponivel = contaOrigem.getSaldo() + contaOrigem.getLimite();
        if ("Poupança".equalsIgnoreCase(contaOrigem.getTipoConta())) {
            throw new IllegalArgumentException("Transferências de conta poupança nao são permitidas");
        }

        if (saldoDisponivel < valor) {
            throw new IllegalArgumentException("Saldo insuficiente para a transferência");
        }

        contaOrigem.setSaldo(contaOrigem.getSaldo() - valor);
        contaDestino.setSaldo(contaDestino.getSaldo() + valor);

        contaRepository.save(contaOrigem);
        contaRepository.save(contaDestino);

        return contaOrigem;
    }

    public Conta sacar(Long idConta, Double valor) {
        Conta conta = contaRepository.findById(idConta).orElse(null);

        if (conta == null) {
            throw new IllegalArgumentException("Conta não encontrada");
        }

        double saldoDisponivel = conta.getSaldo() + conta.getLimite();
        if ("Poupança".equalsIgnoreCase(conta.getTipoConta())) {
            throw new IllegalArgumentException("Saque de conta poupança não é permitido");
        }

        if (saldoDisponivel < valor) {
            throw new IllegalArgumentException("Saldo insuficiente para o saque");
        }

        conta.setSaldo(conta.getSaldo() - valor);
        contaRepository.save(conta);

        return conta;
    }

    public Conta depositar(Long idConta, Double valor) {
        Conta conta = contaRepository.findById(idConta).orElse(null);

        if (conta == null) {
            throw new IllegalArgumentException("Conta não encontrada");
        }

        conta.setSaldo(conta.getSaldo() + valor);
        contaRepository.save(conta);

        return conta;
    }

    public Conta alterarLimite(Long idConta, Double novoLimite) {
        Conta conta = contaRepository.findById(idConta).orElse(null);

        if (conta == null) {
            throw new IllegalArgumentException("Conta não encontrada");
        }

        conta.setLimite(novoLimite);
        contaRepository.save(conta);

        return conta;
    }
}
