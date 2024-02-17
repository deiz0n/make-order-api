package com.deiz0n.makeorder.api.controllers;

import com.deiz0n.makeorder.domain.dtos.ItemDTO;
import com.deiz0n.makeorder.domain.services.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1.0/itens")
public class ItemController {

    private ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<List<ItemDTO>> getItens() {
        List<ItemDTO> itens = itemService.getResouces();
        return ResponseEntity.ok().body(itens);
    }

    @PostMapping("/create")
    public ResponseEntity<ItemDTO> createItem(@RequestBody ItemDTO itemDTO) {
        var item = itemService.createResource(itemDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("{id}")
                .buildAndExpand(item.getId())
                .toUri();
        return ResponseEntity.created(uri).body(item);
    }
}
