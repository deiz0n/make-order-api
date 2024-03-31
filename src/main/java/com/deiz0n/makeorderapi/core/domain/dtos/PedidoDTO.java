package com.deiz0n.makeorderapi.core.domain.dtos;

import com.deiz0n.makeorderapi.core.domain.enums.FormaPagamento;
import com.deiz0n.makeorderapi.core.domain.enums.StatusPedido;
import com.deiz0n.makeorderapi.infrastructure.entities.Mesa;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {

    private UUID id;
    private Instant data;
    @JsonProperty(value = "forma_pagamento")
    private FormaPagamento formaPagamento;
    private StatusPedido status;
    private List<ItemDTO> itens;
    private Integer codigo;
    private FuncionarioDTO funcionario;
    private Mesa mesa;
    
}
