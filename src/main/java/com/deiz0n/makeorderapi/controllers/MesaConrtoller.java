package com.deiz0n.makeorderapi.controllers;

import com.deiz0n.makeorderapi.domain.dtos.MesaDTO;
import com.deiz0n.makeorderapi.domain.entities.Mesa;
import com.deiz0n.makeorderapi.domain.utils.responses.ResponseRequest;
import com.deiz0n.makeorderapi.services.MesaService;
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
public class MesaConrtoller {

    private MesaService service;

    public MesaConrtoller(MesaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<MesaDTO>> getMesas() {
        var mesas = service.getAll();
        return ResponseEntity.ok(mesas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MesaDTO> getMesa(@PathVariable UUID id) {
        var mesa = service.getById(id);
        return ResponseEntity.ok(mesa);
    }

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
