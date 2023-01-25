package br.com.wszd.notas.security;

import br.com.wszd.notas.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserDetailData implements UserDetails {

    public static String email;
    private static String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailData(Optional<Usuario> usuario){
        this.email = usuario.get().getNomeUsuario();
        this.password = usuario.get().getSenha();

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        authorities = usuario.get().getRoles().stream().map(role -> {
            return new SimpleGrantedAuthority("ROLE_" + role.getNome());
        }).collect(Collectors.toList());

        this.authorities = authorities;
    }

    public static UserDetailData create(Optional<Usuario> user){
        return new UserDetailData(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
