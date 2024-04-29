package com.deiz0n.makeorderapi.usecases.pedido;

import com.deiz0n.makeorderapi.core.domain.dtos.PedidoDTO;
import com.deiz0n.makeorderapi.infrastructure.entities.Pedido;

public interface CreatePedidoUseCase {

    public PedidoDTO createPedido(Pedido pedido);

}
