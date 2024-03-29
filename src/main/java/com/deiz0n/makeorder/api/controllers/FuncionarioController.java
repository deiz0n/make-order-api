package com.deiz0n.makeorder.api.controllers;

import com.deiz0n.makeorder.domain.dtos.FuncionarioDTO;
import com.deiz0n.makeorder.domain.services.FuncionarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/funcionarios")
public class FuncionarioController {

    private FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @GetMapping
    public ResponseEntity<List<FuncionarioDTO>> getFuncionarios() {
        List<FuncionarioDTO> funcionarios = funcionarioService.getResouces();
        return ResponseEntity.ok().body(funcionarios);
    }

    @Transactional
    @PostMapping("/create")
    public ResponseEntity<FuncionarioDTO> createFuncionario(@RequestBody @Valid FuncionarioDTO newFuncionario) {
        var funcionario = funcionarioService.createResource(newFuncionario);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(funcionario.getId())
                .toUri();
        return ResponseEntity.created(uri).body(funcionario);
    }

}
