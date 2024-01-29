package com.deiz0n.makeorder.services;

import com.deiz0n.makeorder.dtos.PedidoDTO;
import com.deiz0n.makeorder.models.Pedido;
import com.deiz0n.makeorder.repositories.PedidoRepository;
import com.deiz0n.makeorder.services.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PedidoService {

    private PedidoRepository pedidoRepository;
    private ModelMapper mapper;

    public PedidoService(PedidoRepository pedidoRepository, ModelMapper mapper) {
        this.pedidoRepository = pedidoRepository;
        this.mapper = mapper;
    }

    public List<PedidoDTO> getResources() {
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

    public void deleteResource(UUID id) {
        var pedido = pedidoRepository.getReferenceById(id);
        pedidoRepository.delete(pedido);
    }

    public PedidoDTO updateStatus(UUID id, PedidoDTO pedidoDTO) {
        var pedido = pedidoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("O pedido com id: %s não foi encontrado", id.toString())));
        pedido.setStatusPedido(pedidoDTO.getStatusPedido());
        pedidoRepository.save(pedido);
        return pedidoDTO;
    }

}
