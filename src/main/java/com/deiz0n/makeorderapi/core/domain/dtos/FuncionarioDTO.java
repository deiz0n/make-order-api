package com.deiz0n.makeorderapi.core.domain.dtos;

import com.deiz0n.makeorderapi.core.domain.enums.Cargo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioDTO {

    private UUID id;
    private String nome;
    private String email;
    @JsonProperty(value = "data_nascimento")
    private Date dataNascimento;
    private Cargo cargo;

}
