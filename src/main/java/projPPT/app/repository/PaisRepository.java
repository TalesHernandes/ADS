package projPPT.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import projPPT.app.entities.Pais;

// ALUNOS: PEDRO MASTANDREA - 42204933
//         TALES HERNANDES - 42202639
//         PEDRO PETROSKI  - 32158998

public interface PaisRepository extends JpaRepository<Pais, Long>{
    List<Pais> findByNomeContainingIgnoreCase(String nome);
}
