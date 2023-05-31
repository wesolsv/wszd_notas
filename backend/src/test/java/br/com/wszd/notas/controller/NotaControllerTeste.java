package br.com.wszd.notas.controller;

import br.com.wszd.notas.dto.NotaDTO;
import br.com.wszd.notas.model.Nota;
import br.com.wszd.notas.service.NotaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class NotaControllerTeste {

    @Mock
    private NotaService service;

    @InjectMocks
    private NotaController notaController;

    private MockMvc mockMvc;

    @BeforeEach
    public  void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(notaController).build();
    }

    @Test
    public void devePegarNota() throws Exception {
        Long id =  1L;
        NotaDTO nota = new NotaDTO();
        nota.setId(id);
        nota.setNome("notaTeste");

        when(service.pegarNota(id)).thenReturn(nota);

        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/v1/nota/"+ nota.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(nota.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome", Matchers.is(nota.getNome())));
    }

    @Test
    public void deveCriarNota() throws Exception {
        Nota nota = new Nota();
        nota.setId(1L);
        nota.setNome("notaTeste");

        NotaDTO notaDTO = new NotaDTO();
        notaDTO.setId(nota.getId());
        notaDTO.setNome(nota.getNome());

        when(service.novaNota(nota)).thenReturn(nota);
        URI location = new URI("http://localhost:8080/api/v1/nota/");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/nota/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(nota)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        // Verifica se o cabeçalho de resposta Location contém a localização simulada
        assertThat(response.getHeader("Location")).isEqualTo(location.toString());
    }

    @Test
    public void deveEditarNota() throws Exception {
        Nota nota = new Nota();
        nota.setId(1L);
        nota.setNome("notaTeste");

        NotaDTO notaDTO = new NotaDTO();
        notaDTO.setId(nota.getId());
        notaDTO.setNome(nota.getNome());

        when(service.editarNota(1L, nota)).thenReturn(nota);
        URI location = new URI("http://localhost:8080/api/v1/nota/1/");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/api/v1/nota/" + 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(nota)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        // Verifica se o cabeçalho de resposta Location contém a localização simulada
        assertThat(response.getHeader("Location")).isEqualTo(location.toString());
    }

    @Test
    public void deveDeletarNota() throws Exception {
        service.deletarNota(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/api/v1/nota/" + 1L))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
