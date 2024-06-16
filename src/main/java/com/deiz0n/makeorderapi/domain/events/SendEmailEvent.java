package com.deiz0n.makeorderapi.domain.events;

import com.deiz0n.makeorderapi.domain.utils.Mensagem;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SendEmailEvent extends ApplicationEvent {

    private Mensagem mensagem;

    public SendEmailEvent(Object source, Mensagem mensagem) {
        super(source);
        this.mensagem = mensagem;
    }
}
