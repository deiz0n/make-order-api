package com.deiz0n.makeorderapi.domain.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SendEmailEvent extends ApplicationEvent {

    private String email;

    public SendEmailEvent(Object source, String email) {
        super(source);
        this.email = email;
    }
}
