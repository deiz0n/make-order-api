package com.deiz0n.makeorderapi.core.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItensPedidoDTO {

    private UUID id;
    private Integer quantidade;
    private PedidoDTO pedido;
    private ItemDTO item;

}
