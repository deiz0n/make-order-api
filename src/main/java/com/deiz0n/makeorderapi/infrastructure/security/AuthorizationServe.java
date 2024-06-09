package com.deiz0n.makeorderapi.infrastructure.security;

import com.deiz0n.makeorderapi.repositories.FuncionarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServe implements UserDetailsService {

    private FuncionarioRepository repository;

    public AuthorizationServe(FuncionarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.getByEmail(username);
    }
}
