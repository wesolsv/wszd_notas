package br.com.wszd.notas.repository;

import br.com.wszd.notas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario u WHERE u.nomeUsuario = :nomeUsuario")
    public Usuario findByNomeUsuario(@Param("nomeUsuario") String nomeUsuario);


}
