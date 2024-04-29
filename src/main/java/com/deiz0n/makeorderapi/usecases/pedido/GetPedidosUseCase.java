package com.deiz0n.makeorderapi.usecases.pedido;

import com.deiz0n.makeorderapi.core.domain.dtos.PedidoDTO;

import java.util.List;

public interface GetPedidosUseCase {

    public List<PedidoDTO> getPedidos();

}
