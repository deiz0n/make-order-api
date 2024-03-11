package com.deiz0n.makeorderapi.api.controllers;

import com.deiz0n.makeorderapi.domain.models.Mesa;
import com.deiz0n.makeorderapi.domain.repositories.MesaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1.0/mesas")
public class MesaController {

    private MesaRepository repository;

    @Transactional
    @PostMapping("/create")
    public ResponseEntity<Mesa> createMesa(@RequestBody Mesa newMesa) {
        var mesa = repository.save(newMesa);
        return ResponseEntity.status(HttpStatus.CREATED).body(mesa);
    }
}
