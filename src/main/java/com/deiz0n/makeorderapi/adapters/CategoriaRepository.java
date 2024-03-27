package com.deiz0n.makeorderapi.adapters;

import com.deiz0n.makeorderapi.infrastructure.entities.Categoria;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoriaRepository {

    //Add a entidade categoria do infrastructure como parâmetro
    Categoria createCategoria(Categoria categoria);
    Optional<Categoria> getCategoria(UUID id);
    List<Categoria> getCategorias();

}
