package com.deiz0n.makeorderapi.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> request
                        .requestMatchers("api/v2.0/itens/**").hasRole("COZINHA")
                        .requestMatchers("api/v2.0/itens").hasRole("COZINHA")

                        .requestMatchers("api/v2.0/pedidos/**").hasRole("COZINHA")
                        .requestMatchers("api/v2.0/pedidos").hasRole("COZINHA")

                        .requestMatchers("api/v2.0/categorias/**").hasRole("GARCOM")
                        .requestMatchers("api/v2.0/categorias").hasRole("GARCOM")

                        .requestMatchers("api/v2.0/mesas/**").hasRole("GARCOM")
                        .requestMatchers("api/v2.0/mesas").hasRole("GARCOM")


                        .requestMatchers("api/v2.0/funcionarios/**").hasRole("ADMINISTRACAO")
                        .requestMatchers("api/v2.0/funcionarios").hasRole("ADMINISTRACAO")

                )
                .build();
    }


}
