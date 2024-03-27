package com.deiz0n.makeorderapi.core.domain.dtos;

import com.deiz0n.makeorderapi.core.domain.enums.Cargo;
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
    private String cpf;
    private String email;
    private String senha;
    private Date dataNascimento;
    private Cargo cargo;

}
