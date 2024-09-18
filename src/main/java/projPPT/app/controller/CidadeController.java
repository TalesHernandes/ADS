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

import projPPT.app.entities.Cidade;
import projPPT.app.repository.CidadeRepository;


// ALUNOS: PEDRO MASTANDREA - 42204933
//         TALES HERNANDES - 42202639
//         PEDRO PETROSKI  - 32158998

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository repositorio;

    @GetMapping
    public List<Cidade> getCidades(
            @RequestParam(name = "nome", required = false) String nome
    ){
        if(nome == null){
            return repositorio.findAll();
        }

        return repositorio.findByNomeContainingIgnoreCase(nome);
    }

    @GetMapping("/{id}")
    public Optional<Cidade> getCidadeId(@PathVariable("id") Long id){
        Optional<Cidade> existe = repositorio.findById(id);

        if (existe.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Cidade não encontrado com este ID"
            );
        }

        return repositorio.findById(id);
    }

    @PostMapping
    public ResponseEntity<?> setCidade(@RequestBody Cidade Cidade){
        Optional<Cidade> existe = repositorio.findById(Cidade.getId());

        if(existe.isPresent()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Cidade com ID " + existe.get().getId() + " já existe"
            );
        }

        if (Cidade.getId() != 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Para o método POST, o ID deve ser 0"
            );
        }

        Cidade savedCidade = repositorio.save(Cidade);


        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedCidade.getId()).toUri();

        // retorna o status 201 created com o JSON do Cidade, importante pois contem o ID, no qual é gerado auto
        return ResponseEntity.created(location).body(savedCidade);
    }

    @PutMapping("/{id}")
    public Cidade updateCidade(@RequestBody Cidade Cidade, @PathVariable Long id) {
        Optional<Cidade> existingCidade = repositorio.findById(id);

        if (existingCidade.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Cidade com ID " + id + " não encontrado"
            );
        }

        if (!Objects.equals(existingCidade.get().getId(), Cidade.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Não é possível alterar o ID"
            );
        }

        return repositorio.save(Cidade);
    }

    @DeleteMapping("/{id}")
    public void deleteCidade(@PathVariable Long id){
        Optional<Cidade> existe = repositorio.findById(id);

        if (existe.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Cidade com ID " + id + " não encontrado"
            );
        }

        repositorio.deleteById(id);
    }

}

