package br.com.wszd.notas.controller;

import br.com.wszd.notas.entity.Pessoa;
import br.com.wszd.notas.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/pessoa")
public class PessoaController {

    @Autowired
    private PessoaService service;

    @GetMapping
    private List<Pessoa> listarTodasPessoas (){
        return service.listarTodasPessoas();
    }

    @GetMapping("/{id}")
    private Optional<Pessoa> pegarPessoa (@PathVariable Long id){
        return service.pegarPessoa(id);
    }

    @PostMapping
    private ResponseEntity<Pessoa> novaPessoa(@RequestBody Pessoa nova){
        Pessoa res = service.novaPessoa(nova);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(res.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    private ResponseEntity<Pessoa> editarPessoa(@PathVariable Long id, @RequestBody Pessoa nova){
        Pessoa res = service.editarPessoa(id, nova);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(res.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    private void deletePessoa(@PathVariable Long id){
        service.deletarPessoa(id);
    }
}
