package com.deiz0n.makeorderapi.repositories;

import com.deiz0n.makeorderapi.domain.entities.Comanda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ComandaRepository extends JpaRepository<Comanda, UUID> {
}
