package com.deiz0n.makeorderapi.controllers;

import com.deiz0n.makeorderapi.core.domain.dtos.PedidoDTO;
import com.deiz0n.makeorderapi.infrastructure.entities.Pedido;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v2.0/pedidos")
public interface PedidoController {

    @Transactional
    @PostMapping("/create")
    public ResponseEntity<PedidoDTO> createPedido(@RequestBody @Valid Pedido newPedidoRequest);

    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePedido(@PathVariable UUID id);

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> getPedido(@PathVariable UUID id);

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> getPedidos();

    @Transactional
    @PutMapping("/update/{id}")
    public ResponseEntity<PedidoDTO> updatePedido(@PathVariable UUID id, @RequestBody @Valid Pedido newDataRequest);

    @Transactional
    @PutMapping("/status/{id}")
    public ResponseEntity<PedidoDTO> updateStatus(@PathVariable UUID id, @RequestBody @Valid Pedido newStatusRequest);

}
