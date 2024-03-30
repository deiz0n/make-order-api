package com.deiz0n.makeorderapi.infrastructure.rest.impl;

import com.deiz0n.makeorderapi.adapters.services.PedidoService;
import com.deiz0n.makeorderapi.controllers.PedidoController;
import com.deiz0n.makeorderapi.core.domain.dtos.PedidoDTO;
import com.deiz0n.makeorderapi.infrastructure.entities.Pedido;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Component
public class PedidoControllerImpl implements PedidoController {

    private PedidoService pedidoService;

    public PedidoControllerImpl(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @Override
    public ResponseEntity<PedidoDTO> createPedido(Pedido newPedidoRequest) {
        var pedido = pedidoService.createPedido(newPedidoRequest);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(pedido.getId())
                .toUri();
        return ResponseEntity.created(uri).body(pedido);
    }

    @Override
    public ResponseEntity<?> deletePedido(UUID id) {
        pedidoService.deletePedodo(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<PedidoDTO> getPedido(UUID id) {
        var pedido = pedidoService.getPedido(id);
        return ResponseEntity.ok().body(pedido);
    }

    @Override
    public ResponseEntity<List<PedidoDTO>> getPedidos() {
        var pedidos = pedidoService.getPedidos();
        return ResponseEntity.ok().body(pedidos);
    }

    @Override
    public ResponseEntity<PedidoDTO> updatePedido(UUID id, Pedido newDataRequest) {
        var pedido = pedidoService.updatePedido(id, newDataRequest);
        return ResponseEntity.ok().body(pedido);
    }

    @Override
    public ResponseEntity<PedidoDTO> updateStatus(UUID id, Pedido newStatusRequest) {
        var pedido = pedidoService.updateStatus(id, newStatusRequest);
        return ResponseEntity.ok().body(pedido);
    }
}
