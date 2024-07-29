package com.deiz0n.makeorderapi.services;

import com.deiz0n.makeorderapi.domain.dtos.CategoriaDTO;
import com.deiz0n.makeorderapi.domain.entities.Categoria;
import com.deiz0n.makeorderapi.domain.exceptions.CategoriaExistingException;
import com.deiz0n.makeorderapi.domain.exceptions.CategoriaNotFoundException;
import com.deiz0n.makeorderapi.repositories.CategoriaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    private CategoriaRepository categoriaRepository;
    private ModelMapper mapper;

    public CategoriaService(CategoriaRepository categoriaRepository, ModelMapper mapper) {
        this.categoriaRepository = categoriaRepository;
        this.mapper = mapper;
    }

    public List<CategoriaDTO> getAll() {
        return categoriaRepository.findAll()
                .stream()
                .map(categoria -> mapper.map(categoria, CategoriaDTO.class))
                .collect(Collectors.toList());
    }

    public CategoriaDTO getById(UUID id) {
        return categoriaRepository.findById(id)
                .map(categoria -> mapper.map(categoria, CategoriaDTO.class))
                .orElseThrow(() -> new CategoriaNotFoundException("Não foi possível encontrar uma categoria com o Id informado"));
    }

    public CategoriaDTO create(CategoriaDTO newCategoria) {
        if (categoriaRepository.findByNome(newCategoria.getNome()).isPresent()) throw new CategoriaExistingException("Categoria já cadastrada");
        var categoria = mapper.map(newCategoria, Categoria.class);
        categoriaRepository.save(categoria);
        return newCategoria;
    }

    public void delete (UUID id) {
        var categoria = getById(id);
        categoriaRepository.deleteById(categoria.getId());
    }

}
