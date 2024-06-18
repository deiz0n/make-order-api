package com.deiz0n.makeorderapi.domain.events;

import com.deiz0n.makeorderapi.domain.entities.ItensPedido;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UpdatedItensPedidoEvent extends ApplicationEvent {

    private ItensPedido itensPedido;

    public UpdatedItensPedidoEvent(Object source, ItensPedido itensPedido) {
        super(source);
        this.itensPedido = itensPedido;
    }
}
