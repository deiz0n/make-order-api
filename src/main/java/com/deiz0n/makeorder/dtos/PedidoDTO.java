package com.deiz0n.makeorder.dtos;

import com.deiz0n.makeorder.models.enums.FormaPagamento;
import com.deiz0n.makeorder.models.enums.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {

    private Long id;
    private Instant data;
    private FormaPagamento formaPagamento;
    private StatusPedido statusPedido;

}
