package com.deiz0n.makeorderapi.repositories;

import com.deiz0n.makeorderapi.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PedidoRepository extends JpaRepository<Pedido, UUID> {
}
