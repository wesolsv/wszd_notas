package br.com.wszd.notas.controller;

import br.com.wszd.notas.dto.SessaoDTO;
import br.com.wszd.notas.dto.UserLoginDTO;
import br.com.wszd.notas.service.UsuarioService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/usuario")
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping("/login")
    @ApiOperation(value = "Realiza o Login do usuario e retorna o seu token")
    @ResponseBody
    public SessaoDTO logar(@RequestBody UserLoginDTO infoLogin){
        return service.validarLogin(infoLogin);
    }

    @ApiOperation(value = "Deletando um usuario")
    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id){
        service.deleteUsuario(id);
    }
}
