package com.deiz0n.makeorderapi.domain.dto;

import com.deiz0n.makeorderapi.domain.models.Categoria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDTO {

    private UUID id;
    private String nome;
    private BigDecimal preco;
    private String descricao;
    private Integer quantidade;
    private Categoria categoria;
}
