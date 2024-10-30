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
@CrossOrigin(origins = "http://localhost:3000")
public class ContaController {
    @Autowired
    private ContaService contaService;

    @PostMapping("/{pessoaId}")
    public ResponseEntity<List<Conta>> cadastrarContas(@PathVariable Long pessoaId, @RequestBody List<Conta> contas) {
        try {
            List<Conta> novasContas = contaService.cadastrarContas(pessoaId, contas);
            return ResponseEntity.status(HttpStatus.CREATED).body(novasContas);
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
