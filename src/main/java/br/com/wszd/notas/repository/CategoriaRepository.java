package br.com.wszd.notas.repository;

import br.com.wszd.notas.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
