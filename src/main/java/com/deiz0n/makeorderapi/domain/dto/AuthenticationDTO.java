package com.deiz0n.makeorderapi.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationDTO {

    private String email;
    private String senha;

}
