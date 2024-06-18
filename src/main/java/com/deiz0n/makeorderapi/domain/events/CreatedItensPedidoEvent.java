package com.deiz0n.makeorderapi.domain.events;

import com.deiz0n.makeorderapi.domain.entities.ItensPedido;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CreatedItensPedidoEvent extends ApplicationEvent {

    private ItensPedido itensPedido;

    public CreatedItensPedidoEvent(Object object, ItensPedido itensPedido) {
        super(object);
        this.itensPedido = itensPedido;
    }

}
