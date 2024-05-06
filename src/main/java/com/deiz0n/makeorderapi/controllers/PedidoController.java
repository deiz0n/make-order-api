package com.deiz0n.makeorderapi.controllers;

import com.deiz0n.makeorderapi.domain.dtos.PedidoDTO;
import com.deiz0n.makeorderapi.domain.entities.Pedido;
import com.deiz0n.makeorderapi.domain.enums.StatusPedido;
import com.deiz0n.makeorderapi.services.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v2.0/pedidos")
public class PedidoController {

    private PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> getPedidos() {
        var pedidos = service.getAll();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> getPedido(@PathVariable UUID id) {
        var pedido = service.getById(id);
        return ResponseEntity.ok(pedido);
    }

    @Transactional
    @PostMapping("/create")
    public ResponseEntity<PedidoDTO> createPedido(@RequestBody Pedido request) {
        var pedido = service.create(request);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(pedido.getId())
                .toUri();
        return ResponseEntity.created(uri).body(pedido);
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePedido(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @PutMapping("/update/{id}")
    public ResponseEntity<PedidoDTO> updatePedido(@PathVariable UUID id, @RequestBody Pedido request) {
        var pedido = service.update(id, request);
        return ResponseEntity.ok(pedido);
    }

    @Transactional
    @PatchMapping("/update/status/{id}")
    public ResponseEntity<PedidoDTO> updateStatus(@PathVariable UUID id, @RequestBody StatusPedido request) {
        var pedido = service.updateStatus(id, request);
        return ResponseEntity.ok(pedido);
    }
}
