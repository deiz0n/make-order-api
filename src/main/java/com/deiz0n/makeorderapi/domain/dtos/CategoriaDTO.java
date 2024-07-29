package com.deiz0n.makeorderapi.domain.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID id;
    private String nome;

}
