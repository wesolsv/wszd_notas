package br.com.wszd.notas.controller;

import br.com.wszd.notas.dto.PessoaDTO;
import br.com.wszd.notas.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/pessoa")
public class PessoaController {

    @Autowired
    private PessoaService service;

    @GetMapping
    private List<PessoaDTO> listarTodasPessoas (){
        return service.listarTodasPessoas();
    }

    @GetMapping("/{id}")
    private ResponseEntity<PessoaDTO> pegarPessoa (@PathVariable Long id){
        PessoaDTO res = service.pegarPessoa(id);
        if(res != null){
            return ResponseEntity.ok(res);
        }
        return  ResponseEntity.notFound().build();
    }

    @PostMapping
    private ResponseEntity<br.com.wszd.notas.model.Pessoa> novaPessoa(@RequestBody br.com.wszd.notas.model.Pessoa nova){
        br.com.wszd.notas.model.Pessoa res = service.novaPessoa(nova);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(res.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    private ResponseEntity<br.com.wszd.notas.model.Pessoa> editarPessoa(@PathVariable Long id, @RequestBody br.com.wszd.notas.model.Pessoa nova){
        br.com.wszd.notas.model.Pessoa res = service.editarPessoa(id, nova);
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
