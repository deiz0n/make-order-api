package com.deiz0n.makeorderapi.domain.services;

import com.deiz0n.makeorderapi.domain.dto.CategoriaDTO;
import com.deiz0n.makeorderapi.domain.models.Categoria;
import com.deiz0n.makeorderapi.domain.repositories.CategoriaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private CategoriaRepository categoriaRepository;
    private ModelMapper mapper;

    public CategoriaService(CategoriaRepository categoriaRepository, ModelMapper mapper) {
        this.categoriaRepository = categoriaRepository;
        this.mapper = mapper;
    }

    public List<CategoriaDTO> getResources() {
        return categoriaRepository.findAll()
                .stream()
                .map(x -> mapper.map(x, CategoriaDTO.class))
                .toList();
    }

    public CategoriaDTO createResource(CategoriaDTO newCategoria) {
        var categoria = mapper.map(newCategoria, Categoria.class);
        categoriaRepository.save(categoria);
        return newCategoria;
    }

}
