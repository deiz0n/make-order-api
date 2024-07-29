package com.deiz0n.makeorderapi.domain.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MesaDTO {

    private UUID id;
    private Integer numero;
    private String cliente;

}
