package com.deiz0n.makeorderapi.domain.exceptions;

public class SendEmailException extends RuntimeException {

    public SendEmailException(String msg) {
        super(msg);
    }

}
