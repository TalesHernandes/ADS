package com.projeto.ProjetoFunc.service;

import com.projeto.ProjetoFunc.model.Extrato;
import com.projeto.ProjetoFunc.repository.ExtratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExtratoService {

    @Autowired
    private ExtratoRepository extratoRepository;

    public List<Extrato> listarExtrato() {
        return extratoRepository.findAll();
    }
}
