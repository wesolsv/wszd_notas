package br.com.wszd.notas.repository;

import br.com.wszd.notas.dto.PessoaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PessoaRepository extends JpaRepository<br.com.wszd.notas.model.Pessoa, Long> {
    @Query("SELECT new br.com.wszd.notas.dto.PessoaDTO(p.id, p.nome, p.email ) " +
            "FROM Pessoa p")
    List<PessoaDTO> listaPessoas();

    @Query("SELECT new br.com.wszd.notas.dto.PessoaDTO(p.id, p.nome, p.email ) " +
            "FROM Pessoa p WHERE p.email = :email")
    PessoaDTO findByEmail(@Param("email") String email);
}
