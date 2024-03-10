package com.deiz0n.makeorderapi.api.controllers;

import com.deiz0n.makeorderapi.domain.dto.ItemDTO;
import com.deiz0n.makeorderapi.domain.models.Item;
import com.deiz0n.makeorderapi.domain.services.ItemService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1.0/itens")
public class ItemController {

    private ItemService service;

    public ItemController(ItemService itemService) {
        this.service = itemService;
    }

    @GetMapping
    public ResponseEntity<List<ItemDTO>> getItens() {
        List<ItemDTO> itens = service.getResources();
        return ResponseEntity.ok().body(itens);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemByID(@PathVariable UUID id) {
        var item = service.getByID(id);
        return ResponseEntity.ok().body(item);
    }

    @Transactional
    @PostMapping("/create")
    public ResponseEntity<Item> createItem(@RequestBody ItemDTO newItemRequest) {
        var item = service.createResource(newItemRequest);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("{id}")
                .buildAndExpand(item.getId())
                .toUri();
        return ResponseEntity.created(uri).body(item);
    }

    @Transactional
    @PutMapping("/update/{id}")
    public ResponseEntity<Item> updateItem(@RequestBody @Valid ItemDTO newItemRequest, @PathVariable UUID id) {
        var item = service.updateResource(newItemRequest, id);
        return ResponseEntity.ok().body(item);
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable UUID id) {
        service.deleteResource(id);
        return ResponseEntity.noContent().build();
    }
}
