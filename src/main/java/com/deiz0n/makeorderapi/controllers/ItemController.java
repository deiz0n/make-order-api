package com.deiz0n.makeorderapi.controllers;

import com.deiz0n.makeorderapi.domain.dtos.ItemDTO;
import com.deiz0n.makeorderapi.domain.entities.Item;
import com.deiz0n.makeorderapi.domain.utils.responses.ResponseRequest;
import com.deiz0n.makeorderapi.services.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.yaml.snakeyaml.events.Event;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v2.0/itens")
public class ItemController {

    private ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<List<ItemDTO>> getItens() {
        var pedidos = itemService.getAll();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> getItem(@PathVariable UUID id) {
        var pedido = itemService.getById(id);
        return ResponseEntity.ok(pedido);
    }

    @GetMapping("/top-sales")
    public ResponseEntity<List<Object>> getTopItens() {
        var itens = itemService.getTop();
        return ResponseEntity.ok(itens);
    }

    @Transactional
    @PostMapping("/create")
    public ResponseEntity<ItemDTO> createItem(@RequestBody Item request) {
        var pedido = itemService.create(request);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(pedido.getId())
                .toUri();
        return ResponseEntity.created(uri).body(pedido);
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseRequest> deleteItem(@PathVariable UUID id) {
        itemService.delete(id);

        var response = new ResponseRequest(
                Instant.now(),
                "Recurso excluído",
                String.format("O item com id: {%s} foi excluído com sucesso", id.toString()),
                HttpStatus.NO_CONTENT.value()
        );

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @Transactional
    @PutMapping("/update/{id}")
    public ResponseEntity<ItemDTO> updateItem(@PathVariable UUID id, @RequestBody Item request) {
        var item = itemService.update(id, request);
        return ResponseEntity.ok(item);
    }
}
