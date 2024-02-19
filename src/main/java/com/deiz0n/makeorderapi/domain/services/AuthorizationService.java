package com.deiz0n.makeorderapi.domain.services;

import com.deiz0n.makeorderapi.domain.repositories.FuncionarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    private FuncionarioRepository repository;

    public AuthorizationService(FuncionarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findFirstByEmail(username);
    }
}
