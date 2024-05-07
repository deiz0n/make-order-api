package com.deiz0n.makeorderapi.domain.dtos;

import com.deiz0n.makeorderapi.domain.enums.FormaPagamento;
import com.deiz0n.makeorderapi.domain.enums.StatusPedido;
import com.deiz0n.makeorderapi.domain.entities.Comanda;
import com.deiz0n.makeorderapi.domain.entities.ItensPedido;
import com.deiz0n.makeorderapi.domain.entities.Mesa;
import com.fasterxml.jackson.annotation.JsonInclude;
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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant data;
    @JsonProperty(value = "forma_pagamento")
    private FormaPagamento formaPagamento;
    private StatusPedido status;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer codigo;
    private String observacoes;
    private List<ItensPedido> itens;
    private Comanda comanda;
    private FuncionarioDTO funcionario;
    private Mesa mesa;

    @JsonProperty(value = "valor_total", access = JsonProperty.Access.READ_ONLY)
    public Double getValorTotal() {
        var soma = 0.0;
        for (ItensPedido iten : itens) {
            if (iten.getItem().getPreco() == null) break;
            soma += iten.getQuantidade() * iten.getItem().getPreco().doubleValue();
        }
       return soma;
    }

}
