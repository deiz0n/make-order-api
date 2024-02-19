package com.deiz0n.makeorderapi.repositories;

import com.deiz0n.makeorderapi.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {
}
