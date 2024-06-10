package com.deiz0n.makeorderapi.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    private CustomAuthenticationEntryPoint authenticationEntryPoint;
    private CustomAccessDeniedException accessDeniedException;
    private SecurityFilter securityFilter;

    public SecurityConfig(CustomAuthenticationEntryPoint authenticationEntryPoint, CustomAccessDeniedException accessDeniedException, SecurityFilter securityFilter) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedException = accessDeniedException;
        this.securityFilter = securityFilter;
    }

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
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedException)
                )
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
