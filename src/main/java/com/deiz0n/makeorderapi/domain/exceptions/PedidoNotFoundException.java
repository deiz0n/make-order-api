package com.deiz0n.makeorderapi.domain.exceptions;

public class PedidoNotFoundException extends ResourceNotFoundException{

    public PedidoNotFoundException(String msg) {
        super(msg);
    }
}
