package com.deiz0n.makeorderapi.domain.exceptions;

public class DataIntegrityException extends RuntimeException {

    public DataIntegrityException(String msg) {
        super(msg);
    }

}
