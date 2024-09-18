package projPPT.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import projPPT.app.entities.Cidade;

// ALUNOS: PEDRO MASTANDREA - 42204933
//         TALES HERNANDES - 42202639
//         PEDRO PETROSKI  - 32158998

public interface CidadeRepository extends JpaRepository<Cidade, Long>{
    List<Cidade> findByNomeContainingIgnoreCase(String nome);
}
