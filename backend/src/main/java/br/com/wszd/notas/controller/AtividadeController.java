package br.com.wszd.notas.controller;

import br.com.wszd.notas.dto.AtividadeDTO;
import br.com.wszd.notas.model.Atividade;
import br.com.wszd.notas.service.AtividadeService;
import br.com.wszd.notas.util.StatusAtividade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/atividade")
public class AtividadeController {

    @Autowired
    private AtividadeService service;

    @GetMapping
    private List<AtividadeDTO> listarTodasAtividades (){
        return service.listarTodasAtividades();
    }

    @GetMapping("/bystatus")
    private List<AtividadeDTO> listarTodasAtividadesPorStatus (@RequestParam String status){
        StatusAtividade statusEnum;
        statusEnum = StatusAtividade.valueOf(status);
        return service.listarTodasAtividadesPorStatus(statusEnum);
    }

    @GetMapping("/{id}")
    private AtividadeDTO pegarAtividade (@PathVariable Long id){
        return service.pegarAtividadeDTO(id);
    }

    @PostMapping
    private ResponseEntity<Atividade> novaAtividade(@RequestBody Atividade nova){
        service.novaAtividade(nova);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand("")
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    private ResponseEntity<Atividade> editarAtividade(@PathVariable Long id, @RequestBody Atividade nova){
        service.editarAtividade(id, nova);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand("")
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    private void deleteAtividade(@PathVariable Long id){
        service.cancelarAtividade(id);
    }
}
