package br.com.wszd.notas.controller;

import br.com.wszd.notas.dto.AtividadeDTO;
import br.com.wszd.notas.model.Atividade;
import br.com.wszd.notas.service.AtividadeService;
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
public class AtividadeControllerTeste {

    @Mock
    private AtividadeService service;

    @InjectMocks
    private AtividadeController atividadeController;

    private MockMvc mockMvc;

    @BeforeEach
    public  void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(atividadeController).build();
    }

    @Test
    public void devePegarAtividade() throws Exception {
        Long id =  1L;
        AtividadeDTO atividade = new AtividadeDTO();
        atividade.setId(id);
        atividade.setNome("atividadeTeste");

        when(service.pegarAtividadeDTO(id)).thenReturn(atividade);

        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/v1/atividade/"+ atividade.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(atividade.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome", Matchers.is(atividade.getNome())));
    }

    @Test
    public void deveCriarAtividade() throws Exception {
        Long id =  1L;
        Atividade atividade = new Atividade();
        atividade.setId(id);
        atividade.setNome("atividadeTeste");

        AtividadeDTO atividadeDTO = new AtividadeDTO();
        atividadeDTO.setId(atividade.getId());
        atividadeDTO.setNome(atividade.getNome());

        when(service.novaAtividade(atividade)).thenReturn(atividade);
        URI location = new URI("http://localhost:8080/api/v1/atividade/");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/atividade/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(atividade)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        // Verifica se o cabeçalho de resposta Location contém a localização simulada
        assertThat(response.getHeader("Location")).isEqualTo(location.toString());
    }

    @Test
    public void deveEditarAtividade() throws Exception {
        Long id =  1L;
        Atividade atividade = new Atividade();
        atividade.setId(id);
        atividade.setNome("atividadeTeste");

        AtividadeDTO atividadeDTO = new AtividadeDTO();
        atividadeDTO.setId(atividade.getId());
        atividadeDTO.setNome(atividade.getNome());

        when(service.editarAtividade(1L, atividade)).thenReturn(atividade);
        URI location = new URI("http://localhost:8080/api/v1/atividade/1/");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/api/v1/atividade/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(atividade)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        // Verifica se o cabeçalho de resposta Location contém a localização simulada
        assertThat(response.getHeader("Location")).isEqualTo(location.toString());
    }

    @Test
    public void deveDeletarAtividade() throws Exception {
        service.cancelarAtividade(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/api/v1/atividade/" + 1L))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
