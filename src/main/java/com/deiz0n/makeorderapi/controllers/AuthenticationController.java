package com.deiz0n.makeorderapi.controllers;

import com.deiz0n.makeorderapi.domain.dtos.AuthenticationDTO;
import com.deiz0n.makeorderapi.domain.dtos.TokenDTO;
import com.deiz0n.makeorderapi.domain.entities.Funcionario;
import com.deiz0n.makeorderapi.domain.utils.RecoveryPasswordResponse;
import com.deiz0n.makeorderapi.infrastructure.security.TokenService;
import com.deiz0n.makeorderapi.services.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2.0/auth")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private TokenService tokenService;
    private FuncionarioService funcionarioService;

    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService, FuncionarioService funcionarioService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.funcionarioService = funcionarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> singIn(@RequestBody @Valid AuthenticationDTO request) {
        var user = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha());
        var authentication = authenticationManager.authenticate(user);

        var token = tokenService.generateToken((Funcionario) authentication.getPrincipal());
        return ResponseEntity.ok(new TokenDTO(token));
    }

    @PostMapping("/recovery")
    public ResponseEntity<?> recoveryPassword(@RequestBody RecoveryPasswordResponse request) {
        funcionarioService.recovery(request.getEmail());
        return ResponseEntity.ok("Verify you email");
    }

}
