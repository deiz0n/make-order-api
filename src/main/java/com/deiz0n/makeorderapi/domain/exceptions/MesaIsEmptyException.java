package com.deiz0n.makeorderapi.domain.exceptions;

public class MesaIsEmptyException extends ResourceIsEmptyException{

    public MesaIsEmptyException(String msg) {
        super(msg);
    }
}
