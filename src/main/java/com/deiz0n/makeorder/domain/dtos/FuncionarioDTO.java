package com.deiz0n.makeorder.domain.dtos;

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

    private UUID id;
    private String nome;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String cpf;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha;
    private Date dataNascimento;
    private String cargo;

}
