package com.deiz0n.makeorderapi.infrastructure.persistence.impl;

import com.deiz0n.makeorderapi.adapters.PedidoRepository;
import com.deiz0n.makeorderapi.infrastructure.entities.Pedido;
import com.deiz0n.makeorderapi.infrastructure.persistence.repositories.PedidoRepositoryJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PedidoRepositoryImpl implements PedidoRepository {

    private final PedidoRepositoryJpa repository;

    public PedidoRepositoryImpl(PedidoRepositoryJpa repository) {
        this.repository = repository;
    }

    @Override
    public Pedido createPedido(Pedido newPedidoRequest) {
        return repository.save(newPedidoRequest);
    }

    @Override
    public Optional<Pedido> getPedido(UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<Pedido> getPedidos() {
        return repository.findAll();
    }

    @Override
    public void deletePedido(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public Pedido updatePedido(UUID id, Pedido newDataRequest) {
        var oldData = repository.getReferenceById(id);
        return repository.save(oldData);
    }

    @Override
    public Pedido updateStatus(UUID id, Pedido newStatusRequest) {
        var oldStatus = repository.getReferenceById(id);
        return repository.save(oldStatus);
    }
}
