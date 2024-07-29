package com.deiz0n.makeorderapi.domain.dtos;

import com.deiz0n.makeorderapi.domain.enums.Setor;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID id;
    private String nome;
    private String email;
    @JsonProperty(value = "data_nascimento")
    private Date dataNascimento;
    private Setor setor;

}
