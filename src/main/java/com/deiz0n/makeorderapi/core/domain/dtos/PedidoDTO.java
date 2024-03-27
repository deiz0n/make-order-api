package com.deiz0n.makeorderapi.core.domain.dtos;

import com.deiz0n.makeorderapi.core.domain.enums.FormaPagamento;
import com.deiz0n.makeorderapi.core.domain.enums.StatusPedido;
import com.deiz0n.makeorderapi.infrastructure.entities.Funcionario;
import com.deiz0n.makeorderapi.infrastructure.entities.Item;
import com.deiz0n.makeorderapi.infrastructure.entities.Mesa;
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

    private UUID id;
    private Instant data;
    private FormaPagamento formaPagamento;
    private StatusPedido statusPedido;
    private List<Item> itens;
    private Integer codigo;
    private Funcionario funcionario;
    private Mesa mesa;

}
