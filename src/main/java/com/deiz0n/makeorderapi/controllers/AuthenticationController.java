package com.deiz0n.makeorderapi.controllers;

import com.deiz0n.makeorderapi.domain.utils.requests.AuthenticationRequest;
import com.deiz0n.makeorderapi.domain.utils.responses.ResponseRequest;
import com.deiz0n.makeorderapi.domain.utils.responses.TokenResponse;
import com.deiz0n.makeorderapi.domain.entities.Funcionario;
import com.deiz0n.makeorderapi.domain.utils.requests.RecoveryPasswordRequest;
import com.deiz0n.makeorderapi.infrastructure.security.TokenService;
import com.deiz0n.makeorderapi.services.AuthenticationService;
import com.deiz0n.makeorderapi.services.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/api/v2.0/auth")
public class AuthenticationController {

    private AuthenticationService authenticationService;
    private FuncionarioService funcionarioService;

    public AuthenticationController(AuthenticationService authenticationService, FuncionarioService funcionarioService) {
        this.authenticationService = authenticationService;
        this.funcionarioService = funcionarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> singInAuth(@RequestBody @Valid AuthenticationRequest request) {
        var token = authenticationService.singIn(request);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/recovery")
    public ResponseEntity<ResponseRequest> recoveryPassword(@RequestBody RecoveryPasswordRequest request) {
        funcionarioService.recovery(request.getEmail());

        ResponseRequest response = ResponseRequest
                .builder()
                .instant(Instant.now())
                .title("Email enviado")
                .description("Verifique o código enviado para o seu endereço de email")
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok(response);
    }

}
