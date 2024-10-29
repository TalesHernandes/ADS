package com.projeto.ProjetoFunc.controller;

import com.projeto.ProjetoFunc.model.Conta;
import com.projeto.ProjetoFunc.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/contas")
public class ContaController {
    @Autowired
    private ContaService contaService;

    @PostMapping("/{pessoaId}")
    public ResponseEntity<Conta> cadastrarConta(@PathVariable Long pessoaId, @RequestBody Conta conta) {
        try {
            Conta novaConta = contaService.cadastrarConta(pessoaId, conta);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaConta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @GetMapping
    public ResponseEntity<List<Conta>> listarContas() {
        List<Conta> contas = contaService.listarContas();
        return ResponseEntity.ok(contas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conta> obterContaPorId(@PathVariable Long id) {
        Conta conta = contaService.obterContaPorId(id);
        return conta != null ? ResponseEntity.ok(conta) : ResponseEntity.notFound().build();
    }

    @GetMapping("/pessoa/{pessoaId}")
    public List<Conta> listarContasPorPessoa(@PathVariable Long pessoaId) {
        return contaService.listarContasPorPessoa(pessoaId);
    }
}
