package com.deiz0n.makeorderapi.domain.exceptions;

public class FuncionarioNotFoundException extends ResourceNotFoundException {

    public FuncionarioNotFoundException(String msg) {
        super(msg);
    }
}
