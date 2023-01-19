package br.com.wszd.notas.repository;

import br.com.wszd.notas.dto.PessoaDTO;
import br.com.wszd.notas.model.Categoria;
import br.com.wszd.notas.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    @Query("SELECT c " +
            "FROM Categoria c WHERE c.nome = :categoriaNome AND c.pessoa = :pessoa")
    Categoria pegarCategoriaByName(@Param("categoriaNome") String categoriaNome, @Param("pessoa") Pessoa pessoa);
}
