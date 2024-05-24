package com.deiz0n.makeorderapi.services;

import com.deiz0n.makeorderapi.domain.dtos.PedidoDTO;
import com.deiz0n.makeorderapi.domain.entities.ItensPedido;
import com.deiz0n.makeorderapi.domain.entities.Pedido;
import com.deiz0n.makeorderapi.domain.enums.StatusPedido;
import com.deiz0n.makeorderapi.domain.exceptions.ItensPedidoEmptyException;
import com.deiz0n.makeorderapi.domain.exceptions.PedidoNotFoundException;
import com.deiz0n.makeorderapi.domain.event.ItensPedidoEvent;
import com.deiz0n.makeorderapi.repositories.PedidoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private static final Integer MIN = 1;
    private static final Integer MAX = 1000;

    private PedidoRepository pedidoRepository;
    private ModelMapper mapper;
    private ApplicationEventPublisher publisher;

    public PedidoService(PedidoRepository pedidoRepository, ModelMapper mapper, ApplicationEventPublisher publisher) {
        this.pedidoRepository = pedidoRepository;
        this.mapper = mapper;
        this.publisher = publisher;
    }

    public List<PedidoDTO> getAll() {
        return pedidoRepository.findAll()
                .stream()
                .map(pedidos -> mapper.map(pedidos, PedidoDTO.class))
                .collect(Collectors.toList());
    }

    public PedidoDTO getById(UUID id) {
        return pedidoRepository.findById(id)
                .map(pedido -> mapper.map(pedido, PedidoDTO.class))
                .orElseThrow(() -> new PedidoNotFoundException("Não foi possível encontrar um pedido com o Id informado"));
    }

    public PedidoDTO create(Pedido newPedido) {
        newPedido.setCodigo((int) (Math.random() * MAX) + MIN);
        newPedido.setData(Instant.now());
        newPedido.setStatus(StatusPedido.PENDENTE);

        if (newPedido.getItens().isEmpty()) throw new ItensPedidoEmptyException("Não foram adicionados itens aos pedido");

        var pedido = pedidoRepository.save(newPedido);

        for (ItensPedido item : pedido.getItens()) {
            item.setPedido(pedido);
            var event = new ItensPedidoEvent(this, item);
            publisher.publishEvent(event);
        }
        return mapper.map(pedido, PedidoDTO.class);
    }

    public void delete(UUID id) {
        var pedido = getById(id);
        pedidoRepository.deleteById(pedido.getId());
    }

    public PedidoDTO update(UUID id, Pedido newData) {
        try {
            var pedido = pedidoRepository.getReferenceById(id);

            if (newData.getItens().isEmpty()) {
                BeanUtils.copyProperties(newData, pedido, "id", "codigo", "data", "itens");
                pedidoRepository.save(pedido);
            }
            else {
                BeanUtils.copyProperties(newData, pedido, "id", "codigo", "data");

                for (ItensPedido itens : newData.getItens()) {
                    pedidoRepository.save(pedido);

                    itens.setPedido(pedido);

                    var event = new ItensPedidoEvent("Evento enviado", itens, itens.getId());
                    publisher.publishEvent(event);
                }
            }

            return mapper.map(pedido, PedidoDTO.class);
        } catch (FatalBeanException e) {
            throw new PedidoNotFoundException("Não foi possível encontrar o pedido com o Id informado");
        }
    }

    public PedidoDTO updateStatus(UUID id, Pedido newStatus) {
        var pedido = pedidoRepository.getReferenceById(id);
        pedido.setStatus(newStatus.getStatus());
        pedidoRepository.save(pedido);
        return mapper.map(pedido, PedidoDTO.class);
    }

}
