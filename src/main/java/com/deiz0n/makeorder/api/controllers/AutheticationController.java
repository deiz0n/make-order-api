package com.deiz0n.makeorder.api.controllers;

import com.deiz0n.makeorder.domain.dtos.AuthenticationDTO;
import com.deiz0n.makeorder.domain.models.Funcionario;
import com.deiz0n.makeorder.infrastructure.security.TokenService;
import com.deiz0n.makeorder.domain.dtos.TokenDTO;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1.0/auth")
public class AutheticationController {

    private AuthenticationManager authenticationManager;

    private TokenService tokenService;

    public AutheticationController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @Transactional
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO authenticationDTO) {
        var login = new UsernamePasswordAuthenticationToken(authenticationDTO.getEmail(), authenticationDTO.getSenha());
        var auth = authenticationManager.authenticate(login);

        var token = tokenService.generateToken((Funcionario) auth.getPrincipal());
        var responseToken = new TokenDTO(token);
        return ResponseEntity.ok().body(responseToken);
    }

}
