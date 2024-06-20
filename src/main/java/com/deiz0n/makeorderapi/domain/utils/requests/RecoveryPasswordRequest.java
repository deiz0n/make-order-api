package com.deiz0n.makeorderapi.domain.utils.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecoveryPasswordRequest {

    private String email;

}
