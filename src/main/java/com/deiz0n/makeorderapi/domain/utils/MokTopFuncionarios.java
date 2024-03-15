package com.deiz0n.makeorderapi.domain.utils;

import com.deiz0n.makeorderapi.domain.models.enums.Cargo;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MokTopFuncionarios {

    private String nome;
    private Cargo cargo;
    private Integer vendas;

}
