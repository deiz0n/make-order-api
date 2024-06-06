package com.deiz0n.makeorderapi.services;

import com.deiz0n.makeorderapi.domain.dtos.MesaDTO;
import com.deiz0n.makeorderapi.domain.entities.Mesa;
import com.deiz0n.makeorderapi.domain.exceptions.MesaExistingException;
import com.deiz0n.makeorderapi.domain.exceptions.MesaNotFoundException;
import com.deiz0n.makeorderapi.repositories.MesaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MesaService {

    private MesaRepository repository;

    public MesaService(MesaRepository repository) {
        this.repository = repository;
    }

    public List<MesaDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mesa -> new MesaDTO(mesa.getId(), mesa.getNumero(), mesa.getCliente()))
                .collect(Collectors.toList());
    }

    public MesaDTO getById(UUID id) {
        return repository.findById(id)
                .map(mesa -> new MesaDTO(mesa.getId(), mesa.getNumero(), mesa.getCliente()))
                .orElseThrow(() -> new MesaNotFoundException("Não foi possível encontrar uma mesa com o id informado"));
    }

    public MesaDTO create(Mesa newMesa) {
        if (repository.findByNumero(newMesa.getNumero()).isPresent())
            throw new MesaExistingException("Mesa já cadastrada");

        var mesa = repository.save(newMesa);
        return new MesaDTO(mesa.getId(), mesa.getNumero(), mesa.getCliente());
    }

    public void delete(UUID id) {
        var mesa = getById(id);
        repository.deleteById(mesa.id());
    }
}
