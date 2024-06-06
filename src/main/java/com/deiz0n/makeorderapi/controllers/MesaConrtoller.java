package com.deiz0n.makeorderapi.controllers;

import com.deiz0n.makeorderapi.domain.dtos.MesaDTO;
import com.deiz0n.makeorderapi.domain.entities.Mesa;
import com.deiz0n.makeorderapi.services.MesaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @PostMapping("/create")
    public ResponseEntity<MesaDTO> createMesa(@RequestBody Mesa request) {
        var mesa = service.create(request);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(mesa.id())
                .toUri();
        return ResponseEntity.created(uri).body(mesa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMesa(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
