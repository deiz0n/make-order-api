package com.deiz0n.makeorder.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

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
                        .requestMatchers(HttpMethod.POST, "api/v1.0/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "api/v1.0/funcionarios/create").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1.0/pedidos/create").hasRole("GARCOM")
                        .requestMatchers(HttpMethod.DELETE, "api/v1.0/pedidos/delete/").hasRole("GARCOM")
                        .requestMatchers(HttpMethod.GET, "api/v1.0/pedidos").hasRole("GARCOM")
                        .requestMatchers(HttpMethod.PATCH, "api/v1.0/pedidos/update/status/").hasRole("GARCOM")
                        .requestMatchers(HttpMethod.PATCH, "api/v1.0/pedidos/update/status/").hasRole("COZINHEIRO")
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
}
