package com.deiz0n.makeorderapi.services;

import com.deiz0n.makeorderapi.domain.entities.Funcionario;
import com.deiz0n.makeorderapi.domain.utils.requests.AuthenticationRequest;
import com.deiz0n.makeorderapi.domain.utils.responses.TokenResponse;
import com.deiz0n.makeorderapi.infrastructure.security.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private AuthenticationManager authenticationManager;
    private TokenService tokenService;
    private FuncionarioService funcionarioService;

    public AuthenticationService(AuthenticationManager authenticationManager, TokenService tokenService, FuncionarioService funcionarioService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.funcionarioService = funcionarioService;
    }

    public TokenResponse singIn(AuthenticationRequest newData) {
        var user = new UsernamePasswordAuthenticationToken(newData.getEmail(), newData.getSenha());
        var authentication = authenticationManager.authenticate(user);

        var token = tokenService.generateToken((Funcionario) authentication.getPrincipal());
        return new TokenResponse(token);
    }
}
