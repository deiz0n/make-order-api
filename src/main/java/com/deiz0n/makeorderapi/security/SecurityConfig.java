package com.deiz0n.makeorderapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebSecurityConfigurer<WebSecurity>  {

    private SecurityFilter securityFilter;

    public SecurityConfig(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        //.requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()

                        //.requestMatchers("/swagger-ui/index.html#/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "api/v1.0/auth/login").permitAll()

                        .requestMatchers(HttpMethod.POST, "api/v1.0/funcionarios/create").permitAll()

                        .requestMatchers(HttpMethod.GET, "api/v1.0/categorias").permitAll()
                        .requestMatchers(HttpMethod.POST, "api/v1.0/categorias/create").permitAll()

                        .requestMatchers(HttpMethod.GET, "api/v1.0/itens").hasRole("GARCOM")
                        .requestMatchers(HttpMethod.POST, "api/v1.0/itens/create").hasRole("GARCOM")
                        .requestMatchers(HttpMethod.PUT, "api/v1.0/itens/update/{id}").hasRole("GARCOM")
                        .requestMatchers(HttpMethod.DELETE, "api/v1.0/itens/delete/{id}").hasRole("GARCOM")

                        .requestMatchers(HttpMethod.POST, "/api/v1.0/pedidos/create").hasRole("GARCOM")
                        .requestMatchers(HttpMethod.DELETE, "api/v1.0/pedidos/delete/{id}").hasRole("GARCOM")
                        .requestMatchers(HttpMethod.GET, "api/v1.0/pedidos").hasRole("GARCOM")
                        .requestMatchers(HttpMethod.PUT, "api/v1.0/pedidos/update/{id}").hasRole("GARCOM")
                        .requestMatchers(HttpMethod.PATCH, "api/v1.0/pedidos/update/status/{id}").hasRole("GARCOM")

                        .requestMatchers(HttpMethod.PATCH, "api/v1.0/pedidos/update/status/{id}").hasRole("COZINHEIRO")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    public void init(WebSecurity builder) throws Exception {
    }

    @Override
    public void configure(WebSecurity builder) throws Exception {
        builder.ignoring().requestMatchers
                ("swagger-ui/**", "v3-api-docs/**", "/api-docs/**");
    }
}
