package com.deiz0n.makeorderapi.domain.exceptions;

public class PasswordNotEqualsException extends RuntimeException {

    public PasswordNotEqualsException(String msg) {
        super(msg);
    }

}
