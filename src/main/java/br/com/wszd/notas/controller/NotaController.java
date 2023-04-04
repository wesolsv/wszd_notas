package br.com.wszd.notas.controller;

import br.com.wszd.notas.dto.NotaDTO;
import br.com.wszd.notas.model.Nota;
import br.com.wszd.notas.service.NotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/nota")
public class NotaController {

    @Autowired
    private NotaService service;

    @GetMapping
    private List<NotaDTO> listarTodasNotas (){
        return service.listarTodasNotas();
    }

    @GetMapping("/{id}")
    private NotaDTO pegarNota (@PathVariable Long id){
        return service.pegarNota(id);
    }

    @PostMapping
    private ResponseEntity<Nota> novaNota(@RequestBody Nota nova){
        service.novaNota(nova);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand("")
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    private ResponseEntity<Nota> editarNota(@PathVariable Long id, @RequestBody Nota nova){
        service.editarNota(id, nova);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand("")
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    private void deleteNota(@PathVariable Long id){
        service.deletarNota(id);
    }

    @DeleteMapping()
    private void deleteTodasNotasCategoria(@RequestBody String categoriaNome){
        service.deletarTodasNotasCategoria(categoriaNome);
    }

    @DeleteMapping("/ids")
    private void deleteTodasNotasSelecionadas(@RequestBody List<Long> ids){
        service.deletarTodasNotasIds(ids);
    }
}
