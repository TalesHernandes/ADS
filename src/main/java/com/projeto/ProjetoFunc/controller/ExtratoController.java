package com.projeto.ProjetoFunc.controller;

import com.projeto.ProjetoFunc.service.ExtratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/extrato")
public class ExtratoController {

    @Autowired
    private ExtratoService extratoService;

    @GetMapping("")
    //listar todos os extratos
    public List listarExtrato() {
        return extratoService.listarExtrato();
    }
}
