package br.com.wszd.notas.repository;


import br.com.wszd.notas.model.Logs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogsRepository extends JpaRepository<Logs, Long> {

}
