package br.com.wszd.notas.repository;

import br.com.wszd.notas.entity.Nota;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotaRepository extends JpaRepository<Nota, Long> {
}
