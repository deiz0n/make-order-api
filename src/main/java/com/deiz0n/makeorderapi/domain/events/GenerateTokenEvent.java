package com.deiz0n.makeorderapi.domain.events;

import com.deiz0n.makeorderapi.domain.entities.Funcionario;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class GenerateTokenEvent extends ApplicationEvent {

    private Funcionario funcionario;

    public GenerateTokenEvent(Object source, Funcionario funcionario) {
        super(source);
        this.funcionario = funcionario;
    }
}
