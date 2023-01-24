package br.com.wszd.notas.controller;

import br.com.wszd.notas.model.Usuario;
import br.com.wszd.notas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService service;
    @PostMapping
    public void novoUsuario(@RequestBody Usuario user){
        service.novoUsuario(user);
    }
}
