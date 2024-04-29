package com.deiz0n.makeorderapi.infrastructure.persistence.repositories;

import com.deiz0n.makeorderapi.infrastructure.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoriaRepositoryJpa extends JpaRepository<Categoria, UUID> {

    Optional<Categoria> findByNome(String nome);

}
