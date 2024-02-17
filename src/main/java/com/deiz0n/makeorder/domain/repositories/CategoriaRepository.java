package com.deiz0n.makeorder.domain.repositories;

import com.deiz0n.makeorder.domain.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {
}
