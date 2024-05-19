package com.deiz0n.makeorderapi.event;

import com.deiz0n.makeorderapi.repositories.ItensPedidoRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
public class ItensPedidoCreatedEventListener {

    private ItensPedidoRepository itensPedidoRepository;

    public ItensPedidoCreatedEventListener(ItensPedidoRepository itensPedidoRepository) {
        this.itensPedidoRepository = itensPedidoRepository;
    }

    @EventListener
    public void handleCreatedEvent(ItensPedidoCreatedEvent event) {
        itensPedidoRepository.save(event.getItensPedido());
    }

}
