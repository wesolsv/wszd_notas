package br.com.wszd.notas.repository;

import br.com.wszd.notas.model.Categoria;
import br.com.wszd.notas.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    @Query("SELECT c " +
            "FROM Categoria c WHERE c.nome = :categoriaNome AND c.pessoa.id = :pessoaId")
    Categoria pegarCategoriaByName(@Param("categoriaNome") String categoriaNome, @Param("pessoaId") Long pessoaId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Nota n SET n.categoriaNome = :categoriaNome " +
            "WHERE n.categoria = :cat")
    void atualizarCategoriaNome(@Param("categoriaNome")String categoriaNome, @Param("cat")Categoria cat);

    @Query("SELECT c " +
            "FROM Categoria c WHERE c.pessoa = :pessoa")
    List<Categoria> listarCategoriasPessoa(@Param("pessoa")Pessoa pessoa);
}
