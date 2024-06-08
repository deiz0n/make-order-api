package com.deiz0n.makeorderapi.domain.exceptions;

public class ResourceIsEmptyException extends RuntimeException {

    public ResourceIsEmptyException(String msg) {
        super(msg);
    }
}
