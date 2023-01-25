package br.com.wszd.notas.controller;

import br.com.wszd.notas.dto.SessaoDTO;
import br.com.wszd.notas.dto.UserLoginDTO;
import br.com.wszd.notas.model.Usuario;
import br.com.wszd.notas.service.UsuarioService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    public void novoUsuario(@RequestBody Usuario user){
        service.newUser(user);
    }

    @PostMapping("/login")
    @ApiOperation(value = "Realiza o Login do usuario e retorna o seu token")
    @ResponseBody
    public SessaoDTO logar(@RequestBody UserLoginDTO infoLogin){
        Usuario user = service.findUserByName(infoLogin.getEmail());
        return service.validarLogin(user, infoLogin);
    }

    @ApiOperation(value = "Deletando um usuario")
    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id){
        service.deleteUsuario(id);
    }
}
