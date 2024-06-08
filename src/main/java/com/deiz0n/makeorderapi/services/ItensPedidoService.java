package com.deiz0n.makeorderapi.services;

import com.deiz0n.makeorderapi.domain.events.ItensPedidoEvent;
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

    @EventListener(condition = "#event.id == null")
    public void createListener(ItensPedidoEvent event) {
        itensPedidoRepository.save(event.getItensPedido());
    }

    @EventListener(condition = "#event.id != null ")
    public void updateListener(ItensPedidoEvent event) {
        var itensPedido = itensPedidoRepository.getReferenceById(event.getId());

        BeanUtils.copyProperties(event.getItensPedido(), itensPedido, "id");
        
        itensPedidoRepository.save(itensPedido);
    }

}
