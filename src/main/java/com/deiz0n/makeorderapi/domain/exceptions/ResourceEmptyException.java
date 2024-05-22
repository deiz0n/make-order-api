package com.deiz0n.makeorderapi.domain.exceptions;

public class ResourceEmptyException extends RuntimeException {

    public ResourceEmptyException(String msg) {
        super(msg);
    }
}
