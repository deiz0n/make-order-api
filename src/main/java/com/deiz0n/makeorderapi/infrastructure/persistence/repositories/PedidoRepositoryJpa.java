package com.deiz0n.makeorderapi.infrastructure.persistence.repositories;

import com.deiz0n.makeorderapi.infrastructure.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PedidoRepositoryJpa extends JpaRepository<Pedido, UUID> {
}
