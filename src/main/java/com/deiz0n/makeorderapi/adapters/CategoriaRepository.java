package com.deiz0n.makeorderapi.adapters;

import com.deiz0n.makeorderapi.infrastructure.entities.Categoria;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoriaRepository {

    Categoria createCategoria(Categoria categoria);
    Optional<Categoria> getCategoria(UUID id);
    List<Categoria> getCategorias();

}
