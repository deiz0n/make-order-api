package com.deiz0n.makeorder.api.controllers;

import com.deiz0n.makeorder.domain.dtos.AuthenticationDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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

    public AutheticationController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO authenticationDTO) {
        var login = new UsernamePasswordAuthenticationToken(authenticationDTO.getEmail(), authenticationDTO.getSenha());
        var auth = this.authenticationManager.authenticate(login);

        return ResponseEntity.ok().build();
    }

}
