package br.com.wszd.notas.repository;

import br.com.wszd.notas.model.Atividade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtividadeRepository extends JpaRepository<Atividade, Long> {
}
