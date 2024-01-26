package com.deiz0n.makeorder.dtos;

import com.deiz0n.makeorder.models.enums.FormaPagamento;
import com.deiz0n.makeorder.models.enums.StatusPedido;

import java.time.Instant;

public record PedidoDTO(
        Long id,
        Instant data,
        FormaPagamento formaPagamento,
        StatusPedido statusPedido
) {
}
