package br.com.wszd.notas.controller;

import br.com.wszd.notas.dto.AtividadeDTO;
import br.com.wszd.notas.model.Atividade;
import br.com.wszd.notas.model.Categoria;
import br.com.wszd.notas.service.CategoriaService;
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
public class CategoriaControllerTeste {

    @Mock
    private CategoriaService service;

    @InjectMocks
    private CategoriaController categoriaController;

    private MockMvc mockMvc;

    @BeforeEach
    public  void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(categoriaController).build();
    }

    @Test
    public void devePegarCategoria() throws Exception {
        Long id =  1L;
        Categoria categoria = new Categoria();
        categoria.setId(id);
        categoria.setNome("categoriaTeste");

        when(service.pegarCategoria(id)).thenReturn(categoria);

        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/v1/categoria/"+ categoria.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(categoria.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome", Matchers.is(categoria.getNome())));
    }

    @Test
    public void deveCriarCategoria() throws Exception {
        Long id =  1L;
        Categoria categoria = new Categoria();
        categoria.setId(id);
        categoria.setNome("categoriaTeste");

        when(service.novaCategoria(categoria)).thenReturn(categoria);
        URI location = new URI("http://localhost:8080/api/v1/categoria/");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/categoria/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(categoria)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        // Verifica se o cabeçalho de resposta Location contém a localização simulada
        assertThat(response.getHeader("Location")).isEqualTo(location.toString());
    }

    @Test
    public void deveEditarCategoria() throws Exception {
        Long id =  1L;
        Categoria categoria = new Categoria();
        categoria.setId(id);
        categoria.setNome("categoriaTeste");

        when(service.editarCategoria(1L, categoria)).thenReturn(categoria);
        URI location = new URI("http://localhost:8080/api/v1/categoria/1/");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/api/v1/categoria/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(categoria)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        // Verifica se o cabeçalho de resposta Location contém a localização simulada
        assertThat(response.getHeader("Location")).isEqualTo(location.toString());
    }

    @Test
    public void deveDeletarCategoria() throws Exception {
        service.deletarCategoria(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/api/v1/categoria/" + 1L))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
