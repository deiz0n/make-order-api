package com.deiz0n.makeorderapi.domain.events;

import com.deiz0n.makeorderapi.domain.dtos.FuncionarioDTO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SendEmailEvent extends ApplicationEvent {

    private FuncionarioDTO funcionario;

    public SendEmailEvent(Object source, FuncionarioDTO funcionario) {
        super(source);
        this.funcionario = funcionario;
    }
}
