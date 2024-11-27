package com.projeto.ProjetoFunc.repository;

import com.projeto.ProjetoFunc.model.Extrato;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ExtratoRepository extends JpaRepository<Extrato, Long> {
}
