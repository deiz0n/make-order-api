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

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ModelMapper mapper;

    public CategoriaService(CategoriaRepository categoriaRepository, ModelMapper mapper) {
        this.categoriaRepository = categoriaRepository;
        this.mapper = mapper;
    }

    public List<CategoriaDTO> getAll() {
        return categoriaRepository.findAll()
                .stream()
                .map(categoria -> mapper.map(categoria, CategoriaDTO.class))
                .toList();
    }

    public CategoriaDTO getById(UUID id) {
        return categoriaRepository.findById(id)
                .map(categoria -> mapper.map(categoria, CategoriaDTO.class))
                .orElseThrow(() -> new CategoriaNotFoundException("Não foi possível encontrar uma categoria com o Id informado"));
    }

    public CategoriaDTO create(Categoria newCategoria) {
        if (categoriaRepository.findByNome(newCategoria.getNome()).isPresent()) throw new CategoriaExistingException("Categoria já cadastrada");
        var categoria = categoriaRepository.save(newCategoria);
        return mapper.map(categoria, CategoriaDTO.class);
    }

    public void delete (UUID id) {
        var categoria = getById(id);
        categoriaRepository.deleteById(categoria.getId());
    }

}
