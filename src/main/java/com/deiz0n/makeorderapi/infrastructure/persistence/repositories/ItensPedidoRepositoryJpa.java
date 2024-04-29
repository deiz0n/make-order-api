package com.deiz0n.makeorderapi.infrastructure.persistence.repositories;

import com.deiz0n.makeorderapi.infrastructure.entities.ItensPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItensPedidoRepositoryJpa extends JpaRepository<ItensPedido, UUID> {
}
