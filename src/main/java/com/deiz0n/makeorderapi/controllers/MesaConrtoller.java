package com.deiz0n.makeorderapi.controllers;

import com.deiz0n.makeorderapi.domain.dtos.MesaDTO;
import com.deiz0n.makeorderapi.domain.entities.Mesa;
import com.deiz0n.makeorderapi.domain.utils.responses.ResponseRequest;
import com.deiz0n.makeorderapi.services.MesaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v2.0/mesas")
@Tag(name = "Mesa")
public class MesaConrtoller {

    private MesaService service;

    public MesaConrtoller(MesaService service) {
        this.service = service;
    }

    @Operation(description = "Retorna todos as mesas")
    @GetMapping
    public ResponseEntity<List<MesaDTO>> getMesas() {
        var mesas = service.getAll();
        return ResponseEntity.ok(mesas);
    }

    @Operation(description = "Retorna determinada mesa")
    @GetMapping("/{id}")
    public ResponseEntity<MesaDTO> getMesa(@PathVariable UUID id) {
        var mesa = service.getById(id);
        return ResponseEntity.ok(mesa);
    }

    @Operation(description = "Responsável criar um novo usuário")
    @Transactional
    @PostMapping("/create")
    public ResponseEntity<MesaDTO> createMesa(@RequestBody Mesa request) {
        var mesa = service.create(request);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(mesa.getId())
                .toUri();
        return ResponseEntity.created(uri).body(mesa);
    }

    @Operation(description = "Responsável por excluir uma mesa")
    @Transactional
    @DeleteMapping("delete/{id}")
    public ResponseEntity<ResponseRequest> deleteMesa(@PathVariable UUID id) {
        service.delete(id);

        var response = new ResponseRequest(
                Instant.now(),
                "Recurso excluído",
                String.format("A mesa com id: {%s} foi excluída com sucesso", id.toString()),
                HttpStatus.NO_CONTENT.value()
        );

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
