package com.projeto.ProjetoFunc.controller;

import com.projeto.ProjetoFunc.model.Pessoa;
import com.projeto.ProjetoFunc.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pessoas")
public class
PessoaController {
    @Autowired
    private PessoaService pessoaService;

    @PostMapping
    public ResponseEntity<Pessoa> cadastrarPessoa(@RequestBody Pessoa pessoa) {
        Pessoa novaPessoa = pessoaService.cadastrarPessoa(pessoa);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaPessoa);
    }

    @GetMapping
    public ResponseEntity<List<Pessoa>> listarPessoas() {
        List<Pessoa> pessoas = pessoaService.listarPessoas();
        return ResponseEntity.ok(pessoas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> obterPessoaPorId(@PathVariable Long id) {
        Pessoa pessoa = pessoaService.obterPessoaPorId(id);
        return pessoa != null ? ResponseEntity.ok(pessoa) : ResponseEntity.notFound().build();
    }
}
