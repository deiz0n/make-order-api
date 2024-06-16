package com.deiz0n.makeorderapi.controllers;

import com.deiz0n.makeorderapi.domain.dtos.AuthenticationDTO;
import com.deiz0n.makeorderapi.domain.dtos.TokenDTO;
import com.deiz0n.makeorderapi.domain.entities.Funcionario;
import com.deiz0n.makeorderapi.infrastructure.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2.0/authentication")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private TokenService service;

    public AuthenticationController(AuthenticationManager authenticationManager, TokenService service) {
        this.authenticationManager = authenticationManager;
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> singIn(@RequestBody @Valid AuthenticationDTO request) {
        var user = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha());
        var authentication = authenticationManager.authenticate(user);

        var token = service.generateToken((Funcionario) authentication.getPrincipal());
        return ResponseEntity.ok(new TokenDTO(token));
    }

}
