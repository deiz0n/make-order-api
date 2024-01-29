package com.deiz0n.makeorder.dtos;

import com.deiz0n.makeorder.models.enums.StatusPedido;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {

    private Long id;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Instant data;
    private StatusPedido statusPedido;

}
