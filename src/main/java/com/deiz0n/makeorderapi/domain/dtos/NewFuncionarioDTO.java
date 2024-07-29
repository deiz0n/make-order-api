package com.deiz0n.makeorderapi.domain.dtos;

import com.deiz0n.makeorderapi.domain.enums.Setor;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewFuncionarioDTO {

    private String nome;
    @CPF(message = "CPF inválido")
    private String cpf;
    @Email(message = "Email inválido")
    private String email;
    private String senha;
    @JsonProperty(value = "data_nascimento")
    private Date dataNascimento;
    private Setor setor;


}
