package br.com.wszd.notas.controller;

import br.com.wszd.notas.dto.PessoaDTO;
import br.com.wszd.notas.model.Pessoa;
import br.com.wszd.notas.service.PessoaService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.net.URI;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class PessoaControllerTeste {

    @Mock
    private PessoaService service;

    @InjectMocks
    private PessoaController pessoaController;

    private MockMvc mockMvc;

    @BeforeEach
    public  void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(pessoaController).build();
    }

    @Test
    public void devePegarPessoa() throws Exception {
        Long pessoaId =  1L;
        PessoaDTO pessoa = new PessoaDTO();
        pessoa.setId(pessoaId);
        pessoa.setEmail("teste@teste.com.br");

        when(service.pegarPessoaDTO(pessoaId)).thenReturn(pessoa);

        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/v1/pessoa/"+ pessoa.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(pessoa.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is(pessoa.getEmail())));
    }

    @Test
    public void deveCriarPessoa() throws Exception {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setEmail("teste@teste.com.br");

        PessoaDTO pessoadto = new PessoaDTO();
        pessoadto.setId(pessoa.getId());
        pessoadto.setEmail(pessoa.getEmail());

        when(service.novaPessoaDTO(any(Pessoa.class))).thenReturn(pessoadto);
        URI location = new URI("http://localhost:8080/api/v1/pessoa/1");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/pessoa/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(pessoa)))
                        .andExpect(MockMvcResultMatchers.status().isCreated())
                        .andReturn();

        MockHttpServletResponse response = result.getResponse();

        // Verifica se o corpo da resposta é o objeto de DTO simulado
        assertThat(response.getContentAsString())
                .isEqualTo(new ObjectMapper().writeValueAsString(pessoadto));

        // Verifica se o cabeçalho de resposta Location contém a localização simulada
        assertThat(response.getHeader("Location")).isEqualTo(location.toString());
    }

    @Test
    public void deveEditarPessoa() throws Exception {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setEmail("teste@teste.com.br");

        Pessoa pessoaret = new Pessoa();

        when(service.editarPessoa(1L,pessoa)).thenReturn(pessoaret);
        URI location = new URI("http://localhost:8080/api/v1/pessoa/" +1L+"/");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/api/v1/pessoa/" + 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(pessoa)))
                        .andExpect(MockMvcResultMatchers.status().isCreated())
                        .andReturn();

        MockHttpServletResponse response = result.getResponse();

        // Verifica se o cabeçalho de resposta Location contém a localização simulada
        assertThat(response.getHeader("Location")).isEqualTo(location.toString());
    }

    @Test
    public void deveDeletarPessoa() throws Exception {
        service.deletarPessoa(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/api/v1/pessoa/" + 1L))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
