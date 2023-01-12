package br.com.wszd.notas.controller;

import br.com.wszd.notas.entity.Atividade;
import br.com.wszd.notas.service.AtividadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/atividade")
public class AtividadeController {

    @Autowired
    private AtividadeService service;

    @GetMapping
    private List<Atividade> listarTodasAtividades (){
        return service.listarTodasAtividades();
    }

    @GetMapping("/{id}")
    private Optional<Atividade> pegarAtividade (@PathVariable Long id){
        return service.pegarAtividade(id);
    }

    @PostMapping
    private ResponseEntity<Atividade> novaAtividade(@RequestBody Atividade nova){
        Atividade res = service.novaAtividade(nova);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(res.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    private ResponseEntity<Atividade> editarAtividade(@PathVariable Long id, @RequestBody Atividade nova){
        Atividade res = service.editarAtividade(id, nova);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(res.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    private void deleteAtividade(@PathVariable Long id){
        service.deletarAtividade(id);
    }
}
