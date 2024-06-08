package com.deiz0n.makeorderapi.infrastructure.security;

import com.deiz0n.makeorderapi.repositories.FuncionarioRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class AuthorizationServe implements UserDetailsService {

    private FuncionarioRepository repository;

    public AuthorizationServe(FuncionarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.getByUsername(username);
    }
}
