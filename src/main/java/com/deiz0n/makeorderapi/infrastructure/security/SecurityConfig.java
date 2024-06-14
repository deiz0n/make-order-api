package com.deiz0n.makeorderapi.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    private SecurityFilter securityFilter;
    private CustomAuthenticationEntryPoint authenticationEntryPoint;
    private CustomAccessDeniedException accessDeniedException;

    public SecurityConfig(SecurityFilter securityFilter, CustomAuthenticationEntryPoint authenticationEntryPoint, CustomAccessDeniedException accessDeniedException) {
        this.securityFilter = securityFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedException = accessDeniedException;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> request

                        .requestMatchers(HttpMethod.GET, "api/v2.0/pedidos").hasRole("ADMINISTRACAO")
                        .requestMatchers(HttpMethod.GET, "api/v2.0/mesas").hasRole("GARCOM")
                        .requestMatchers(HttpMethod.GET, "api/v2.0/itens").hasRole("COZINHA")

                        .requestMatchers(HttpMethod.POST, "api/v2.0/authentication/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "api/v2.0/funcionarios/create").permitAll()

                        .requestMatchers(HttpMethod.GET, "api/v2.0/funcionarios").permitAll()

                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(handlingConfigurer -> handlingConfigurer
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
