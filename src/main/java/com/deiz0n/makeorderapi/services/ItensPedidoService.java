package com.deiz0n.makeorderapi.services;

import com.deiz0n.makeorderapi.domain.events.CreatedItensPedidoEvent;
import com.deiz0n.makeorderapi.domain.events.UpdatedItensPedidoEvent;
import com.deiz0n.makeorderapi.repositories.ItensPedidoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ItensPedidoService {

    private ItensPedidoRepository itensPedidoRepository;

    public ItensPedidoService(ItensPedidoRepository itensPedidoRepository) {
        this.itensPedidoRepository = itensPedidoRepository;
    }

    @EventListener
    public void createListener(CreatedItensPedidoEvent event) {
        itensPedidoRepository.save(event.getItensPedido());
    }

    @EventListener
    public void updateListener(UpdatedItensPedidoEvent event) {
        var itensPedido = itensPedidoRepository.getReferenceById(event.getItensPedido().getId());

        BeanUtils.copyProperties(event.getItensPedido(), itensPedido, "id");
        
        itensPedidoRepository.save(itensPedido);
    }

}
