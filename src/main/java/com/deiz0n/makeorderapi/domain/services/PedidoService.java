package com.deiz0n.makeorderapi.domain.services;

import com.deiz0n.makeorderapi.domain.dto.PedidoDTO;
import com.deiz0n.makeorderapi.domain.models.Pedido;
import com.deiz0n.makeorderapi.domain.repositories.PedidoRepository;
import com.deiz0n.makeorderapi.domain.services.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
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

    public Pedido createResource(PedidoDTO newPedidoRequest) {
        var pedido = mapper.map(newPedidoRequest, Pedido.class);
        var teste = pedidoRepository.save(pedido);
        return pedidoRepository.save(teste);
    }

    public void deleteResource(UUID id) {
        var pedido = findByID(id);
        pedidoRepository.delete(pedido);
    }

    public PedidoDTO updateStatus(UUID id, PedidoDTO newStatusRequest) {
        var pedido = findByID(id);
        pedido.setStatusPedido(newStatusRequest.getStatusPedido());
        pedidoRepository.save(pedido);
        return newStatusRequest;
    }

    public PedidoDTO updatePedido(UUID id, PedidoDTO newPedidoRequest) {
        var pedido = findByID(id);
        BeanUtils.copyProperties(newPedidoRequest, pedido, "id");
        pedidoRepository.save(pedido);
        return newPedidoRequest;
    }

    public Pedido findByID(UUID id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("O pedido com id: %s não foi encontrado", id.toString())));
    }
}
