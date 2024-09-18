package projPPT.app.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

// ALUNOS: PEDRO MASTANDREA - 42204933
//         TALES HERNANDES - 42202639
//         PEDRO PETROSKI  - 32158998

@Entity
public class Pais {

    @Id
    @GeneratedValue
    private Long id;
    private String nome;
    private String continente;
    private Long populacao;
    
}
