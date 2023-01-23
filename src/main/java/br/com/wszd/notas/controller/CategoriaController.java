package br.com.wszd.notas.controller;

import br.com.wszd.notas.model.Categoria;
import br.com.wszd.notas.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @GetMapping
    private List<Categoria> listarTodasCategorias (){
        return service.listarTodasCategorias();
    }

    @GetMapping("/{id}")
    private Categoria pegarCategoria (@PathVariable Long id){
        return service.pegarCategoria(id);
    }

    @PostMapping
    private ResponseEntity<Categoria> novaCategoria(@RequestBody Categoria nova){
        Categoria res = service.novaCategoria(nova);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(res.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    private ResponseEntity<Categoria> editarCategoria(@PathVariable Long id, @RequestBody Categoria nova){
        Categoria res = service.editarCategoria(id, nova);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(res.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    private void deleteCategoria(@PathVariable Long id){
        service.deletarCategoria(id);
    }
}
