package com.deiz0n.makeorderapi.domain.exceptions;

public class ItemNotFoundException extends ResourceNotFoundException {

    public ItemNotFoundException(String msg) {
        super(msg);
    }
}
