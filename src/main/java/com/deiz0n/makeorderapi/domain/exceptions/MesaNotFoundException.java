package com.deiz0n.makeorderapi.domain.exceptions;

public class MesaNotFoundException extends ResourceNotFoundException {

    public MesaNotFoundException(String msg) {
        super(msg);
    }
}
