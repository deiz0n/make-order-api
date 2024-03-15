package com.deiz0n.makeorderapi.api.controllers;

import com.deiz0n.makeorderapi.domain.dto.FuncionarioDTO;
import com.deiz0n.makeorderapi.domain.models.Funcionario;
import com.deiz0n.makeorderapi.domain.services.FuncionarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/funcionarios")
public class FuncionarioController {

    private final FuncionarioService service;
    @Value("${api.security.token.secret}")
    private String secret;

    public FuncionarioController(FuncionarioService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<FuncionarioDTO>> getFuncionarios() {
        List<FuncionarioDTO> funcionarios = service.getResouces();
        return ResponseEntity.ok().body(funcionarios);
    }

    @GetMapping("/login")
    public ResponseEntity<FuncionarioDTO> getFuncionarioByToken(@AuthenticationPrincipal Funcionario funcionario) {
        var user = service.getResourceByToken(funcionario);
        return ResponseEntity.ok().body(user);
    }

    @Transactional
    @PostMapping("/create")
    public ResponseEntity<FuncionarioDTO> createFuncionario(@RequestBody @Valid FuncionarioDTO newFuncionario) {
        var funcionario = service.createResource(newFuncionario);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(funcionario.getId())
                .toUri();
        return ResponseEntity.created(uri).body(funcionario);
    }

}
