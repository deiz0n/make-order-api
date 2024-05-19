package com.deiz0n.makeorderapi.event;

import com.deiz0n.makeorderapi.domain.entities.ItensPedido;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationEvent;

import java.util.List;


@Getter
public class ItensPedidoCreatedEvent extends ApplicationEvent {

    private ItensPedido itensPedido;

    public ItensPedidoCreatedEvent(Object object, ItensPedido itensPedido) {
        super(object);
        this.itensPedido = itensPedido;
    }

}
