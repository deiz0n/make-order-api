package com.deiz0n.makeorder.api.controllers;

import com.deiz0n.makeorder.domain.dtos.PedidoDTO;
import com.deiz0n.makeorder.domain.services.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1.0/pedidos")
public class PedidoController {

    private PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> getPedidos() {
        List<PedidoDTO> pedidos = pedidoService.getResources();
        return ResponseEntity.ok().body(pedidos);
    }

    @Transactional
    @PostMapping("/create")
    public ResponseEntity<PedidoDTO> createPedido(@RequestBody PedidoDTO newPedido) {
        newPedido.setData(Instant.now());
        var pedido = pedidoService.createResource(newPedido);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(pedido.getId())
                .toUri();
        return ResponseEntity.created(uri).body(newPedido);
    }

    @Transactional
    @PatchMapping("/update/status/{id}")
    public ResponseEntity<PedidoDTO> updateStatus(@PathVariable UUID id, @RequestBody PedidoDTO newStatus) {
        var pedido = pedidoService.updateStatus(id, newStatus);
        return ResponseEntity.ok().body(pedido);
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePedido(@PathVariable UUID id) {
        pedidoService.deleteResource(id);
        return ResponseEntity.noContent().build();
    }

}
