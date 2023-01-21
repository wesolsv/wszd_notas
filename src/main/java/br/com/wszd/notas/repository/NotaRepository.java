package br.com.wszd.notas.repository;

import br.com.wszd.notas.dto.NotaDTO;
import br.com.wszd.notas.model.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotaRepository extends JpaRepository<Nota, Long> {

    @Query("SELECT new br.com.wszd.notas.dto.NotaDTO(n.id, n.nome, n.conteudo, n.dataCriacao, n.dataAlteracao, n.categoriaNome) " +
            "FROM Nota n")
    List<NotaDTO> pegarTodasNotas();

    @Query("SELECT new br.com.wszd.notas.dto.NotaDTO(n.id, n.nome, n.conteudo, n.dataCriacao, n.dataAlteracao, n.categoriaNome) " +
            "FROM Nota n " +
            "WHERE n.id = :id")
    NotaDTO pegarNota(@Param("id") Long id);
}
