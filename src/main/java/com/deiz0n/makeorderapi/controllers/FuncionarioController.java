package com.deiz0n.makeorderapi.controllers;

import com.deiz0n.makeorderapi.domain.dtos.FuncionarioDTO;
import com.deiz0n.makeorderapi.domain.entities.Funcionario;
import com.deiz0n.makeorderapi.domain.utils.responses.ResponseRequest;
import com.deiz0n.makeorderapi.services.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v2.0/funcionarios")
public class FuncionarioController {

    private FuncionarioService service;

    public FuncionarioController(FuncionarioService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<FuncionarioDTO>> getFuncionarios() {
        var funcionarios = service.getAll();
        return ResponseEntity.ok(funcionarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioDTO> getFuncionario(@PathVariable UUID id) {
        var funcionario = service.getById(id);
        return ResponseEntity.ok(funcionario);
    }

    @GetMapping("/top-sales")
    public ResponseEntity<List<Object>> getTopFuncionarios() {
        var funcionarios = service.getTop();
        return ResponseEntity.ok(funcionarios);
    }

    @Transactional
    @PostMapping("/create")
    public ResponseEntity<FuncionarioDTO> createFuncionario(@RequestBody @Valid Funcionario request) {
        var funcionario = service.create(request);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(funcionario.getId())
                .toUri();
        return ResponseEntity.created(uri).body(funcionario);
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseRequest> deleteFuncionario(@PathVariable UUID id) {
        service.delete(id);

        var response = new ResponseRequest(
                Instant.now(),
                "Recurso excluído",
                String.format("O funcionário com id: {%s} foi excluído com sucesso", id.toString()),
                HttpStatus.NO_CONTENT.value()
        );

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @Transactional
    @PutMapping("/update/{id}")
    public ResponseEntity<FuncionarioDTO> updateFuncionario(@PathVariable UUID id, @RequestBody @Valid Funcionario request) {
        var funcionario = service.update(id, request);
        return ResponseEntity.ok(funcionario);
    }

}
