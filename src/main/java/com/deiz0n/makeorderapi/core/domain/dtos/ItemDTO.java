package com.deiz0n.makeorderapi.core.domain.dtos;

import com.deiz0n.makeorderapi.infrastructure.entities.Categoria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {

    private UUID id;
    private String nome;
    private BigDecimal preco;
    private String descricao;
    private Integer quantidade;
    private Categoria categoria;
}
