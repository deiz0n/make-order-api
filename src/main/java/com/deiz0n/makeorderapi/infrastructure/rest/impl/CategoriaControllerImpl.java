package com.deiz0n.makeorderapi.infrastructure.rest.impl;

import com.deiz0n.makeorderapi.adapters.services.CategoriaService;
import com.deiz0n.makeorderapi.core.domain.dtos.CategoriaDTO;
import com.deiz0n.makeorderapi.infrastructure.entities.Categoria;
import com.deiz0n.makeorderapi.infrastructure.rest.CategoriaController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Component
public class CategoriaControllerImpl implements CategoriaController {

    private CategoriaService categoriaService;

    public CategoriaControllerImpl(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @Override
    public ResponseEntity<CategoriaDTO> createCategoria(Categoria newRequestCategoria) {
        var categoria = categoriaService.createCategoria(newRequestCategoria);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("{id}")
                .buildAndExpand(categoria.getId())
                .toUri();
        return ResponseEntity.created(uri).body(categoria);
    }

    @Override
    public ResponseEntity<CategoriaDTO> getCategoria(UUID id) {
        var categoria = categoriaService.getCategoria(id);
        return ResponseEntity.ok().body(categoria);
    }

    @Override
    public ResponseEntity<List<CategoriaDTO>> getCategorias() {
        var categorias = categoriaService.getCategorias();
        return ResponseEntity.ok().body(categorias);
    }
}
