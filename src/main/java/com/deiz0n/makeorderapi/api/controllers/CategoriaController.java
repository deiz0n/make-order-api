package com.deiz0n.makeorderapi.api.controllers;

import com.deiz0n.makeorderapi.domain.dto.CategoriaDTO;
import com.deiz0n.makeorderapi.domain.services.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/categorias")
public class CategoriaController {

    private CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> getCategorias() {
        List<CategoriaDTO> categorias = categoriaService.getResources();
        return ResponseEntity.ok().body(categorias);
    }

    @PostMapping("/create")
    public ResponseEntity<CategoriaDTO> createCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        var categoria = categoriaService.createResource(categoriaDTO);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("{id}")
                .buildAndExpand(categoria.getId())
                .toUri();
        return ResponseEntity.created(uri).body(categoria);
    }
}
