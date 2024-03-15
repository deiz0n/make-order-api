package com.deiz0n.makeorderapi.domain.services;

import com.deiz0n.makeorderapi.domain.dto.PedidoDTO;
import com.deiz0n.makeorderapi.domain.models.Pedido;
import com.deiz0n.makeorderapi.domain.repositories.PedidoRepository;
import com.deiz0n.makeorderapi.domain.services.exceptions.CreatedOrderException;
import com.deiz0n.makeorderapi.domain.services.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PedidoService implements ServiceCRUD<PedidoDTO, Pedido> {

    private final PedidoRepository repository;
    private final ModelMapper mapper;

    public PedidoService(PedidoRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<PedidoDTO> getResources() {
        return repository.findAll()
                .stream()
                .map(x -> mapper.map(x, PedidoDTO.class))
                .toList();
    }

    public Pedido createResource(PedidoDTO newPedidoRequest) {
        var pedido = mapper.map(newPedidoRequest, Pedido.class);
        if (newPedidoRequest.getItens() == null) throw new CreatedOrderException();
        return repository.save(pedido);
    }

    public void deleteResource(UUID id) {
        var pedido = findByID(id);
        repository.delete(pedido);
    }

    public Pedido updateStatus(UUID id, PedidoDTO newStatusRequest) {
        var pedido = findByID(id);
        pedido.setStatusPedido(newStatusRequest.getStatusPedido());
        return repository.save(pedido);
    }

    @Override
    public Pedido updateResource(PedidoDTO newPedidoRequest, UUID id) {
        var pedido = findByID(id);
        BeanUtils.copyProperties(newPedidoRequest, pedido, "id");
        return repository.save(pedido);
    }

    public Pedido findByID(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("O pedido com id: %s não foi encontrado", id.toString())));
    }

}
