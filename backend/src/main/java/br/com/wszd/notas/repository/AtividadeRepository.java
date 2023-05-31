package br.com.wszd.notas.repository;

import br.com.wszd.notas.dto.AtividadeDTO;
import br.com.wszd.notas.model.Atividade;
import br.com.wszd.notas.model.Pessoa;
import br.com.wszd.notas.util.StatusAtividade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AtividadeRepository extends JpaRepository<Atividade, Long> {

    @Query("SELECT new br.com.wszd.notas.dto.AtividadeDTO(a.id, a.nome, a.conteudo, a.dataLembrete, a.status) " +
            "FROM Atividade a WHERE a.pessoa = :pessoa")
    List<AtividadeDTO> pegarTodasAtividades(@Param("pessoa") Pessoa pessoa);

    @Query("SELECT new br.com.wszd.notas.dto.AtividadeDTO(a.id, a.nome, a.conteudo, a.dataLembrete, a.status) " +
            "FROM Atividade a WHERE a.pessoa = :pessoa AND a.status = :status")
    List<AtividadeDTO> listarTodasAtividadesPorStatus(@Param("pessoa") Pessoa pessoa, @Param("status")StatusAtividade status);
}
