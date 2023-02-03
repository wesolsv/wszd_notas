package br.com.wszd.notas.repository;

import br.com.wszd.notas.dto.NotaDTO;
import br.com.wszd.notas.model.Nota;
import br.com.wszd.notas.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotaRepository extends JpaRepository<Nota, Long> {

    @Query("SELECT new br.com.wszd.notas.dto.NotaDTO(n.id, n.nome, n.conteudo, n.dataCriacao, n.dataAlteracao, n.categoriaNome) " +
            "FROM Nota n WHERE n.pessoa = :pessoa")
    List<NotaDTO> pegarTodasNotas(@Param("pessoa") Pessoa pessoa);

    @Query("SELECT new br.com.wszd.notas.dto.NotaDTO(n.id, n.nome, n.conteudo, n.dataCriacao, n.dataAlteracao, n.categoriaNome) " +
            "FROM Nota n WHERE n.categoriaNome = :nomeCategoria")
    List<NotaDTO> pegarTodasNotasCategoria(@Param("nomeCategoria")String nomeCategoria);

    @Query("SELECT new br.com.wszd.notas.dto.NotaDTO(n.id, n.nome, n.conteudo, n.dataCriacao, n.dataAlteracao, n.categoriaNome) " +
            "FROM Nota n " +
            "WHERE n.id = :id")
    NotaDTO pegarNota(@Param("id") Long id);

    @Query("SELECT new br.com.wszd.notas.dto.NotaDTO(n.id, n.nome, n.conteudo, n.dataCriacao, n.dataAlteracao, n.categoriaNome) " +
            "FROM Nota n WHERE n.id IN :ids")
    List<NotaDTO> pegarTodasNotasIds(List<Long> ids);
}
