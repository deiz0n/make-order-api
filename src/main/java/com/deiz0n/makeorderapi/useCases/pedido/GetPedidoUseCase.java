package com.deiz0n.makeorderapi.useCases.pedido;

import com.deiz0n.makeorderapi.core.domain.dtos.PedidoDTO;

import java.util.UUID;

public interface GetPedidoUseCase {

    public PedidoDTO getPedido(UUID id);

}
