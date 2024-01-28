package com.deiz0n.makeorder.services;

import com.deiz0n.makeorder.dtos.PedidoDTO;
import com.deiz0n.makeorder.models.Pedido;
import com.deiz0n.makeorder.repositories.PedidoRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.record.RecordModule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    private PedidoRepository pedidoRepository;
    private ModelMapper mapper;

    public PedidoService(PedidoRepository pedidoRepository, ModelMapper mapper) {
        this.pedidoRepository = pedidoRepository;
        this.mapper = mapper;
    }

    public List<PedidoDTO> getResources() {
        mapper.registerModule(new RecordModule());
        List<PedidoDTO> pedidos = pedidoRepository.findAll()
                .stream()
                .map(x -> mapper.map(x, PedidoDTO.class))
                .toList();

        return pedidos;
    }

    public Pedido createResource(PedidoDTO newPedido) {
        var pedido = mapper.map(newPedido, Pedido.class);
        return pedidoRepository.save(pedido);
    }

}
