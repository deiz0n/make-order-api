package com.deiz0n.makeorderapi.domain.repositories;

import com.deiz0n.makeorderapi.domain.models.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MesaRepository extends JpaRepository<Mesa, UUID> {
}
