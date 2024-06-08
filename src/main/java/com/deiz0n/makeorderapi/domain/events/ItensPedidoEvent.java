package com.deiz0n.makeorderapi.domain.events;

import com.deiz0n.makeorderapi.domain.entities.ItensPedido;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@Getter
public class ItensPedidoEvent extends ApplicationEvent {

    private ItensPedido itensPedido;
    private UUID id;

    public ItensPedidoEvent(Object object, ItensPedido itensPedido) {
        super(object);
        this.itensPedido = itensPedido;
    }

    public ItensPedidoEvent(Object source, ItensPedido itensPedido, UUID id) {
        super(source);
        this.itensPedido = itensPedido;
        this.id = id;
    }

}
