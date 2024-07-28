package com.deiz0n.makeorderapi.controllers;

import com.deiz0n.makeorderapi.domain.dtos.ItemDTO;
import com.deiz0n.makeorderapi.domain.entities.Item;
import com.deiz0n.makeorderapi.domain.utils.responses.ResponseRequest;
import com.deiz0n.makeorderapi.services.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v2.0/itens")
@Tag(name = "Item")
public class ItemController {

    private ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @Deprecated
    public ResponseEntity<List<ItemDTO>> getItens() {
        var pedidos = itemService.getAll();
        return ResponseEntity.ok(pedidos);
    }

    @Operation(description = "Retorna determinado item")
    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> getItem(@PathVariable UUID id) {
        var pedido = itemService.getById(id);
        return ResponseEntity.ok(pedido);
    }

    @Operation(description = "Retorna os 3 itens mais vendidos")
    @GetMapping("/top-sales")
    public ResponseEntity<List<Object>> getTopItens() {
        var itens = itemService.getTop();
        return ResponseEntity.ok(itens);
    }

    @Operation(description = "Responsável por criar um novo item")
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

    @Operation(description = "Responsável por excluir um item")
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

    @Operation(description = "Responsável por atualizar os dados de determinado item")
    @Transactional
    @PutMapping("/update/{id}")
    public ResponseEntity<ItemDTO> updateItem(@PathVariable UUID id, @RequestBody Item request) {
        var item = itemService.update(id, request);
        return ResponseEntity.ok(item);
    }
}
