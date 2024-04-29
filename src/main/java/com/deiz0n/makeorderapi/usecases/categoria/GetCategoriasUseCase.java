package com.deiz0n.makeorderapi.usecases.categoria;

import com.deiz0n.makeorderapi.core.domain.dtos.CategoriaDTO;

import java.util.Collection;

public interface GetCategoriasUseCase {

    public Collection<CategoriaDTO> getCategorias();

}

