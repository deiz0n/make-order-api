package com.deiz0n.makeorderapi.infrastructure.persistence.impl;

import com.deiz0n.makeorderapi.adapters.ItensPedidoRepository;
import com.deiz0n.makeorderapi.infrastructure.entities.ItensPedido;
import com.deiz0n.makeorderapi.infrastructure.persistence.repositories.ItensPedidoRepositoryJpa;
import org.springframework.stereotype.Repository;

@Repository
public class ItensPedidoRepositoryImpl implements ItensPedidoRepository {

    private final ItensPedidoRepositoryJpa repository;

    public ItensPedidoRepositoryImpl(ItensPedidoRepositoryJpa repository) {
        this.repository = repository;
    }

    @Override
    public ItensPedido createItensPedido(ItensPedido itensPedido) {
        return repository.save(itensPedido);
    }
}
