package com.deiz0n.makeorderapi.adapters;

import com.deiz0n.makeorderapi.infrastructure.entities.Pedido;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PedidoRepository {

    Pedido createPedido(Pedido pedido);
    Optional<Pedido> getPedido(UUID id);
    List<Pedido> getPedidos();
    void deletePedido(UUID id);
    Pedido updatePedido(UUID id, Pedido pedido);
    Pedido updateStatus(UUID id, Pedido pedido);

}
