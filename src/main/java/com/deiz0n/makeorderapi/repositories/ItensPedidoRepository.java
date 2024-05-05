package com.deiz0n.makeorderapi.repositories;

import com.deiz0n.makeorderapi.domain.entities.ItensPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItensPedidoRepository extends JpaRepository<ItensPedido, UUID> {
}
