package com.deiz0n.makeorder.repositories;

import com.deiz0n.makeorder.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PedidoRepository extends JpaRepository<Pedido, UUID> {
}
