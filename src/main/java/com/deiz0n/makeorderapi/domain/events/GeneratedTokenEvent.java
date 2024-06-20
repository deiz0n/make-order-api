package com.deiz0n.makeorderapi.domain.events;

import com.deiz0n.makeorderapi.domain.utils.responses.TokenResponse;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class GeneratedTokenEvent extends ApplicationEvent {

    private TokenResponse response;

    public GeneratedTokenEvent(Object source, TokenResponse response) {
        super(source);
        this.response = response;
    }
}
