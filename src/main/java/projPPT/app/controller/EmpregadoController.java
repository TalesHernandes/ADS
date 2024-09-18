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

import projPPT.app.entities.Empregado;
import projPPT.app.repository.EmpregadoRepository;

// ALUNOS: PEDRO MASTANDREA - 42204933
//         TALES HERNANDES - 42202639
//         PEDRO PETROSKI  - 32158998

@RestController
@RequestMapping("/empregados")
public class EmpregadoController {
    
    @Autowired
    private EmpregadoRepository repositorio;

    @GetMapping
    public List<Empregado> getEmpregados(
            @RequestParam(name = "nome", required = false) String nome
    ){
        if(nome == null){
            return repositorio.findAll();
        }

        return repositorio.findByNomeContainingIgnoreCase(nome);
    }

    @GetMapping("/{id}")
    public Optional<Empregado> getEmpregadoId(@PathVariable("id") Long id){
        Optional<Empregado> existe = repositorio.findById(id);

        if (existe.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Empregado não encontrado com este ID"
            );
        }

        return repositorio.findById(id);
    }

    @PostMapping 
    public ResponseEntity<?> setEmpregado(@RequestBody Empregado empregado){
        Optional<Empregado> existe = repositorio.findById(empregado.getId());

        if(existe.isPresent()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Empregado com ID " + existe.get().getId() + " já existe"
            );
        }

        if (empregado.getId() != 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Para o método POST, o ID deve ser 0"
            );
        }

        Empregado savedEmpregado = repositorio.save(empregado);


        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedEmpregado.getId()).toUri();

        // retorna o status 201 created com o JSON do empregado, importante pois contem o ID, no qual é gerado auto
        return ResponseEntity.created(location).body(savedEmpregado);
    }

    @PutMapping("/{id}")
    public Empregado updateEmpregado(@RequestBody Empregado empregado, @PathVariable Long id) {
        Optional<Empregado> existingEmpregado = repositorio.findById(id);

        if (existingEmpregado.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Empregado com ID " + id + " não encontrado"
            );
        }

        if (!Objects.equals(existingEmpregado.get().getId(), empregado.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Não é possível alterar o ID"
            );
        }

        return repositorio.save(empregado);
    }

    @DeleteMapping("/{id}")
    public void deleteEmpregado(@PathVariable Long id){
        Optional<Empregado> existe = repositorio.findById(id);

        if (existe.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Empregado com ID " + id + " não encontrado"
            );
        }

        repositorio.deleteById(id);
    }

}
