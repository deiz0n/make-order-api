package com.deiz0n.makeorderapi.security;

import com.auth0.jwt.JWT;
import com.deiz0n.makeorderapi.domain.repositories.FuncionarioRepository;
import com.deiz0n.makeorderapi.domain.services.exceptions.ResourceNotFoundException;
import com.deiz0n.makeorderapi.domain.utils.CustomEvent;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {


    private final TokenService tokenService;

    private final FuncionarioRepository funcionarioRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    public SecurityFilter(TokenService tokenService, FuncionarioRepository funcionarioRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.tokenService = tokenService;
        this.funcionarioRepository = funcionarioRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = recoveryToken(request);
        if (token != null) {
            var login = tokenService.validateToken(token);
            UserDetails user = funcionarioRepository.findFirstByEmail(login);
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String recoveryToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }

}
