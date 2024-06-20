package com.deiz0n.makeorderapi.domain.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConfirmCode {

    private String code;

    @Override
    public String toString() {
        return code;
    }
}
