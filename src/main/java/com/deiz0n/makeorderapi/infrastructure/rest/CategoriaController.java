package com.deiz0n.makeorderapi.infrastructure.rest;

import com.deiz0n.makeorderapi.core.domain.dtos.CategoriaDTO;
import com.deiz0n.makeorderapi.infrastructure.entities.Categoria;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v2.0/categorias")
public interface CategoriaController {

    @Transactional
    @PostMapping("/create")
    public ResponseEntity<CategoriaDTO> createCategoria(@RequestBody @Valid Categoria newRequestCategoria);

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> getCategoria(@PathVariable UUID id);

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> getCategorias();

}
