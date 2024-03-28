package com.deiz0n.makeorderapi.useCases.pedido;

import com.deiz0n.makeorderapi.core.domain.dtos.PedidoDTO;
import com.deiz0n.makeorderapi.infrastructure.entities.Pedido;

import java.util.UUID;

public interface UpdateStatusPedidoUseCase {

    public PedidoDTO updateStatus(UUID id, Pedido pedido);

}
