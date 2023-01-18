package br.com.wszd.notas.repository;

import br.com.wszd.notas.dto.PessoaDTO;
import br.com.wszd.notas.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    @Query("SELECT new br.com.wszd.notas.dto.PessoaDTO(p.id, p.nome, p.email ) " +
            "FROM Pessoa p")
    List<PessoaDTO> listaPessoas();
}
