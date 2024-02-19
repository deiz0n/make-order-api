package com.deiz0n.makeorderapi.domain.dto;

import com.deiz0n.makeorderapi.domain.models.enums.Cargo;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioDTO {

    private UUID id;
    private String nome;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @CPF(message = "CPF inválido")
    private String cpf;
    @Email(message = "Email inválido")
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha;
    private Date dataNascimento;
    private Cargo cargo;

}
