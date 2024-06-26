package com.deiz0n.makeorderapi.controllers;

import com.deiz0n.makeorderapi.domain.utils.requests.AuthenticationRequest;
import com.deiz0n.makeorderapi.domain.utils.requests.ResetPasswordRequest;
import com.deiz0n.makeorderapi.domain.utils.responses.ResponseRequest;
import com.deiz0n.makeorderapi.domain.utils.responses.TokenResponse;
import com.deiz0n.makeorderapi.domain.utils.requests.RecoveryPasswordRequest;
import com.deiz0n.makeorderapi.services.AuthenticationService;
import com.deiz0n.makeorderapi.services.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

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

        var response = new ResponseRequest(
                Instant.now(),
                "Email enviado",
                "Verifique o endereço enviado para o seu endereço de email",
                HttpStatus.OK.value()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/recovery/user")
    public ResponseEntity<ResponseRequest> resetPassword(@RequestParam(name = "id") UUID funcionarioId, @RequestBody ResetPasswordRequest request) {
        authenticationService.resetPassword(request, funcionarioId);

        var response = new ResponseRequest(
                Instant.now(),
                "Senha alterada",
                "Sua senha foi alterada com sucesso",
                HttpStatus.OK.value()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/authenticated")
    public ResponseEntity<?> authenticated(Authentication authentication) {
        var funcionario = funcionarioService.getBySession(authentication);
        return ResponseEntity.ok(funcionario);
    }
}
