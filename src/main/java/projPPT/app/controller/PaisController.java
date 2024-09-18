package projPPT.app.controller;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import projPPT.app.entities.Pais;
import projPPT.app.repository.PaisRepository;

// ALUNOS: PEDRO MASTANDREA - 42204933
//         TALES HERNANDES - 42202639
//         PEDRO PETROSKI  - 32158998


@RestController
@RequestMapping("/paises")
public class PaisController {

    @Autowired
    private PaisRepository repositorio;

    @GetMapping
    public List<Pais> getPais(
            @RequestParam(name = "nome", required = false) String nome
    ){
        if(nome == null){
            return repositorio.findAll();
        }

        return repositorio.findByNomeContainingIgnoreCase(nome);
    }

    @GetMapping("/{id}")
    public Optional<Pais> getPaisId(@PathVariable("id") Long id){
        Optional<Pais> existe = repositorio.findById(id);

        if (existe.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Pais não encontrado com este ID"
            );
        }

        return repositorio.findById(id);
    }

    @PostMapping
    public ResponseEntity<?> setPais(@RequestBody Pais Pais){
        Optional<Pais> existe = repositorio.findById(Pais.getId());

        if(existe.isPresent()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Pais com ID " + existe.get().getId() + " já existe"
            );
        }

        if (Pais.getId() != 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Para o método POST, o ID deve ser 0"
            );
        }

        Pais savedPais = repositorio.save(Pais);


        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedPais.getId()).toUri();

        // retorna o status 201 created com o JSON do Pais, importante pois contem o ID, no qual é gerado auto
        return ResponseEntity.created(location).body(savedPais);
    }

    @PutMapping("/{id}")
    public Pais updatePais(@RequestBody Pais Pais, @PathVariable Long id) {
        Optional<Pais> existingPais = repositorio.findById(id);

        if (existingPais.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Pais com ID " + id + " não encontrado"
            );
        }

        if (!Objects.equals(existingPais.get().getId(), Pais.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Não é possível alterar o ID"
            );
        }

        return repositorio.save(Pais);
    }

    @DeleteMapping("/{id}")
    public void deletePais(@PathVariable Long id){
        Optional<Pais> existe = repositorio.findById(id);

        if (existe.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Pais com ID " + id + " não encontrado"
            );
        }

        repositorio.deleteById(id);
    }

}
