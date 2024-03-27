package com.deiz0n.makeorderapi.useCases;

import com.deiz0n.makeorderapi.core.domain.dtos.CategoriaDTO;
import com.deiz0n.makeorderapi.core.exceptions.CategoriaNotFoundException;

import java.util.UUID;

public interface GetCategoriaUseCase {

    public CategoriaDTO getCategoria(UUID id) throws CategoriaNotFoundException;

}
