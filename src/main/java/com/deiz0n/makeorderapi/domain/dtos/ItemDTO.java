package com.deiz0n.makeorderapi.domain.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID id;
    private String nome;
    private BigDecimal preco;
    private String descricao;
    @JsonProperty(value = "quantidade_disponivel")
    private Integer quantidadeDisponivel;
    private CategoriaDTO categoria;

}
