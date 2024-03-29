package com.deiz0n.makeorder.domain.dtos;

import com.deiz0n.makeorder.domain.models.Item;
import com.deiz0n.makeorder.domain.models.enums.FormaPagamento;
import com.deiz0n.makeorder.domain.models.enums.StatusPedido;
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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Instant data;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, value = "forma_pagamento")
    private FormaPagamento formaPagamento;
    @JsonProperty("status_pedido")
    private StatusPedido statusPedido;
    List<Item> itens;

    @JsonProperty(value = "valor_total")
    public Double getValorTotal() {
        var soma = 0.0;
        for (Item x : itens) {
            soma += x.getPreco().doubleValue() * x.getQuantidade();
        }
        return soma;
    }
}
