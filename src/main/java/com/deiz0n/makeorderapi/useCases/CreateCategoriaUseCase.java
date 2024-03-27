package com.deiz0n.makeorderapi.useCases;

import com.deiz0n.makeorderapi.core.domain.dtos.CategoriaDTO;
import com.deiz0n.makeorderapi.core.exceptions.CategoriaExistingException;
import com.deiz0n.makeorderapi.infrastructure.entities.Categoria;

public interface CreateCategoriaUseCase {

    public CategoriaDTO createCategoria(Categoria categoria) throws CategoriaExistingException;

}
