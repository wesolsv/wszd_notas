package br.com.wszd.notas.controller;

import br.com.wszd.notas.dto.SessaoDTO;
import br.com.wszd.notas.dto.UserLoginDTO;
import br.com.wszd.notas.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTeste {

    @Mock
    private UsuarioService service;

    @InjectMocks
    private UsuarioController usuarioController;

    private MockMvc mockMvc;

    @BeforeEach
    public  void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
    }

    @Test
    public void deveCriarUsuario() throws Exception {
        UserLoginDTO user = new UserLoginDTO("teste@email.com", "123456");

        SessaoDTO sessaoDTO = new SessaoDTO();
        sessaoDTO.setLogin(user.getEmail());
        sessaoDTO.setToken("testeToken");

        when(service.validarLogin(user)).thenReturn(sessaoDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/usuario/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                        .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
