package com.deiz0n.makeorderapi.repositories;

import com.deiz0n.makeorderapi.domain.entities.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MesaRepository extends JpaRepository<Mesa, UUID> {

    Optional<Mesa> findByNumero(Integer numero);

}
