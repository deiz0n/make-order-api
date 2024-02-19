package com.deiz0n.makeorderapi.api.controllers;

import com.deiz0n.makeorderapi.domain.dto.PedidoDTO;
import com.deiz0n.makeorderapi.domain.services.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1.0/pedidos")
@Tag(name = "Pedido Controller")
public class PedidoController {

    private PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    @Operation(summary = "Lista os pedidos realizados")
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "200",
                description = "Listagem dos pedidos realizadas com sucesso",
                content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PedidoDTO.class))}),
            @ApiResponse(
                    responseCode = "403",
                    description = "O usuário não possui permissão para realizar tal ação"
            )

    })
    public ResponseEntity<List<PedidoDTO>> getPedidos() {
        List<PedidoDTO> pedidos = pedidoService.getResources();
        return ResponseEntity.ok().body(pedidos);
    }

    @Transactional
    @PostMapping("/create")
    @Operation(summary = "Cria um novo pedido")
    @ApiResponses(value =
            {
            @ApiResponse(responseCode = "201",
                description = "Pedido criado com sucesso",
                content = {@Content(mediaType = "application/json",
                        schema = @Schema(implementation = PedidoDTO.class))}),
            @ApiResponse(
                responseCode = "403",
                description = "O usuário não possui permissão para realizar tal ação"
            )
            }
    )
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
    @Operation(summary = "Atualiza o status de determinado pedido")
    @ApiResponses(value =
            {
                    @ApiResponse(
                        responseCode = "200",
                        description = "Status atualizado com sucesso",
                        content = {@Content(mediaType = "application/json",
                        schema = @Schema(implementation = PedidoDTO.class))}),
                    @ApiResponse(
                        responseCode = "404",
                        description = "Pedido não encontrado"),
                    @ApiResponse(
                            responseCode = "403",
                            description = "O usuário não possui permissão para realizar tal ação"
                    )
            }
    )
    public ResponseEntity<PedidoDTO> updateStatus(@PathVariable UUID id, @RequestBody PedidoDTO newStatus) {
        var pedido = pedidoService.updateStatus(id, newStatus);
        return ResponseEntity.ok().body(pedido);
    }

    @Transactional
    @PutMapping("/update/{id}")
    @Operation(summary = "Atualiza informações de determinado pedido")
    @ApiResponses(value =
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Pedido atualizado com sucesso",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PedidoDTO.class))}),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Pedido não encontrado"),
                    @ApiResponse(
                            responseCode = "403",
                            description = "O usuário não possui permissão para realizar tal ação"
                    )
            }
    )
    public ResponseEntity<PedidoDTO> updatePedido(@PathVariable UUID id, @RequestBody PedidoDTO newPedidoRequest) {
        var pedido = pedidoService.updatePedido(id, newPedidoRequest);
        return ResponseEntity.ok().body(pedido);
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Exclui determinado pedido")
    @ApiResponses(value =
            {
                    @ApiResponse(
                        responseCode = "204",
                        description = "Pedido excluído com sucesso",
                        content = {@Content(mediaType = "application/json")}),
                    @ApiResponse(
                        responseCode = "404",
                        description = "Pedido não encontrado"),
                    @ApiResponse(
                            responseCode = "403",
                            description = "O usuário não possui permissão para realizar tal ação"
                    )
            }
    )
    public ResponseEntity<?> deletePedido(@PathVariable UUID id) {
        pedidoService.deleteResource(id);
        return ResponseEntity.noContent().build();
    }

}
