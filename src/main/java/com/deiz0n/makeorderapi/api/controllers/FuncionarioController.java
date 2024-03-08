package com.deiz0n.makeorderapi.api.controllers;

import com.deiz0n.makeorderapi.domain.dto.FuncionarioDTO;
import com.deiz0n.makeorderapi.domain.services.FuncionarioService;
import com.deiz0n.makeorderapi.domain.utils.CustomEvent;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/funcionarios")
public class FuncionarioController {

    private final FuncionarioService service;

    public FuncionarioController(FuncionarioService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<FuncionarioDTO>> getFuncionarios() {
        List<FuncionarioDTO> funcionarios = service.getResouces();
        return ResponseEntity.ok().body(funcionarios);
    }

//    @GetMapping("/login")
//    @EventListener
//    public ResponseEntity<Object> getFuncionarioByToken(CustomEvent event) {
//        return ResponseEntity.ok().body(service.getResourceByToken(event));
//    }

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
