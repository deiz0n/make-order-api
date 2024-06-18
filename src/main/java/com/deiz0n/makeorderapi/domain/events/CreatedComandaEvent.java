package com.deiz0n.makeorderapi.domain.events;

import com.deiz0n.makeorderapi.domain.entities.Comanda;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CreatedComandaEvent extends ApplicationEvent {

    private Comanda comanda;

    public CreatedComandaEvent(Object source, Comanda comanda) {
        super(source);
        this.comanda = comanda;
    }
}
