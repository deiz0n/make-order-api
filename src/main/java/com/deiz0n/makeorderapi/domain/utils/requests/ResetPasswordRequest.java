package com.deiz0n.makeorderapi.domain.utils.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResetPasswordRequest {

    private String password;
    @JsonProperty(value = "confirm_password")
    private String confirmPassword;

}
