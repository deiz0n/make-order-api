package com.deiz0n.makeorder.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationDTO {

    private String email;
    private String senha;

}
