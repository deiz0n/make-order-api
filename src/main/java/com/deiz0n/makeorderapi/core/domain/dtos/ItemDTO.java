package com.deiz0n.makeorderapi.core.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {

    private UUID id;
    private String nome;
    private BigDecimal preco;
    private String descricao;
    @JsonProperty(value = "quantidade_disponivel")
    private Integer quantidadeDisponivel;
    private Integer quantidade;
    private CategoriaDTO categoria;
    @JsonIgnore
    private List<PedidoDTO> pedidos;

}
