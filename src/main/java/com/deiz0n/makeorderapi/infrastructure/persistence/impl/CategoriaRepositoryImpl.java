package com.deiz0n.makeorderapi.infrastructure.persistence.impl;

import com.deiz0n.makeorderapi.adapters.CategoriaRepository;
import com.deiz0n.makeorderapi.infrastructure.entities.Categoria;
import com.deiz0n.makeorderapi.infrastructure.persistence.repositories.CategoriaRepositoryJpa;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CategoriaRepositoryImpl implements CategoriaRepository {

    private final CategoriaRepositoryJpa repository;

    public CategoriaRepositoryImpl(CategoriaRepositoryJpa repository) {
        this.repository = repository;
    }

    @Override
    public Categoria createCategoria(Categoria newCategoriaRequest) {
        return repository.save(newCategoriaRequest);
    }

    @Override
    public Optional<Categoria> getCategoria(UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<Categoria> getCategorias() {
        return repository.findAll();
    }
}
