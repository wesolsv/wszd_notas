package br.com.wszd.notas.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private static final String[] SWAGGER_WHITELIST = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
    };

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(SWAGGER_WHITELIST).permitAll()
                .antMatchers(HttpMethod.POST,"/api/v1/pessoa").anonymous()
                .antMatchers(HttpMethod.POST,"/api/v1/usuario/login").permitAll()
                .antMatchers(HttpMethod.PUT,"/api/v1/pessoa/{id}").hasAnyRole( "USER", "ADMIN")
                .antMatchers(HttpMethod.GET,"/api/v1/pessoa/{id}").hasAnyRole( "USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE,"/api/v1/pessoa/**").hasAnyRole( "USER", "ADMIN")
                .antMatchers(HttpMethod.POST,"/api/v1/nota").hasAnyRole( "USER", "ADMIN")
                .antMatchers(HttpMethod.PUT,"/api/v1/nota/{id}").hasAnyRole( "USER", "ADMIN")
                .antMatchers(HttpMethod.GET,"/api/v1/nota/{id}").hasAnyRole( "USER", "ADMIN")
                .antMatchers(HttpMethod.GET,"/api/v1/nota").hasAnyRole( "USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE,"/api/v1/nota/**").hasAnyRole( "USER", "ADMIN")
                .antMatchers(HttpMethod.POST,"/api/v1/categoria").hasAnyRole( "USER", "ADMIN")
                .antMatchers(HttpMethod.PUT,"/api/v1/categoria/{id}").hasAnyRole( "USER", "ADMIN")
                .antMatchers(HttpMethod.GET,"/api/v1/categoria/{id}").hasAnyRole( "USER", "ADMIN")
                .antMatchers(HttpMethod.POST,"/api/v1/atividade").hasAnyRole( "USER", "ADMIN")
                .antMatchers(HttpMethod.PUT,"/api/v1/atividade/{id}").hasAnyRole( "USER", "ADMIN")
                .antMatchers(HttpMethod.GET,"/api/v1/atividade/{id}").hasAnyRole( "USER", "ADMIN")
                .antMatchers(HttpMethod.GET,"/api/v1/atividade").hasAnyRole( "USER", "ADMIN")
                .anyRequest().hasAnyRole("ADMIN")
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(new JWTFilter(), UsernamePasswordAuthenticationFilter.class).build();
    }
}
