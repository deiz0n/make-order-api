package com.deiz0n.makeorderapi.usecases.pedido;

import com.deiz0n.makeorderapi.core.domain.dtos.PedidoDTO;
import com.deiz0n.makeorderapi.infrastructure.entities.Pedido;

import java.util.UUID;

public interface UpdatePedidoUseCase {

    public PedidoDTO updatePedido(UUID ID, Pedido pedido);

}
