package com.deiz0n.makeorderapi.domain.dto;

import com.deiz0n.makeorderapi.domain.models.Funcionario;
import com.deiz0n.makeorderapi.domain.models.Item;
import com.deiz0n.makeorderapi.domain.models.Mesa;
import com.deiz0n.makeorderapi.domain.models.enums.FormaPagamento;
import com.deiz0n.makeorderapi.domain.models.enums.StatusPedido;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {

    private UUID id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant data;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, value = "forma_pagamento")
    private FormaPagamento formaPagamento;
    @JsonProperty("status_pedido")
    private StatusPedido statusPedido;
    private List<Item> itens;
    private Integer codigo;
    private Funcionario funcionario;
    private Mesa mesa;

    @JsonProperty(value = "valor_total")
    public Double getValorTotal() {
        var soma = 0.0;
        for (Item x : itens) {
            soma += x.getPreco().doubleValue() * x.getQuantidade();
        }
        return soma;
    }
}
