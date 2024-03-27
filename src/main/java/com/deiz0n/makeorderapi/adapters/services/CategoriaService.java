package com.deiz0n.makeorderapi.adapters.services;

import com.deiz0n.makeorderapi.core.domain.dtos.CategoriaDTO;
import com.deiz0n.makeorderapi.core.exceptions.CategoriaExistingException;
import com.deiz0n.makeorderapi.core.exceptions.CategoriaNotFoundException;
import com.deiz0n.makeorderapi.infrastructure.config.ModelMapperConfig;
import com.deiz0n.makeorderapi.infrastructure.entities.Categoria;
import com.deiz0n.makeorderapi.infrastructure.persistence.impl.CategoriaRepositoryImpl;
import com.deiz0n.makeorderapi.useCases.CreateCategoriaUseCase;
import com.deiz0n.makeorderapi.useCases.GetCategoriaUseCase;
import com.deiz0n.makeorderapi.useCases.GetCategoriasUseCase;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoriaService implements CreateCategoriaUseCase, GetCategoriaUseCase, GetCategoriasUseCase {


    private CategoriaRepositoryImpl categoriaRepository;
    private ModelMapperConfig mapperConfig;

    public CategoriaService(CategoriaRepositoryImpl categoriaRepository, ModelMapperConfig mapperConfig) {
        this.categoriaRepository = categoriaRepository;
        this.mapperConfig = mapperConfig;
    }

    @Override
    public CategoriaDTO createCategoria(Categoria newCategoriaRequest) throws CategoriaExistingException {
        var categoria = categoriaRepository.createCategoria(newCategoriaRequest);
        return mapperConfig.modelMapper().map(categoria, CategoriaDTO.class);
    }

    @Override
    public CategoriaDTO getCategoria(UUID id) throws CategoriaNotFoundException {
        return categoriaRepository.getCategoria(id)
                .map(categoria -> mapperConfig.modelMapper().map(categoria, CategoriaDTO.class))
                .orElseThrow(() -> new CategoriaNotFoundException("Categoria não encontrada"));
    }

    @Override
    public List<CategoriaDTO> getCategorias() {
        return categoriaRepository.getCategorias()
                .stream()
                .map(categoria -> mapperConfig.modelMapper().map(categoria, CategoriaDTO.class))
                .toList();
    }
}
