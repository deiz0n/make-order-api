package com.deiz0n.makeorderapi.services;

import com.deiz0n.makeorderapi.domain.dtos.MesaDTO;
import com.deiz0n.makeorderapi.domain.entities.Mesa;
import com.deiz0n.makeorderapi.domain.exceptions.DataIntegrityException;
import com.deiz0n.makeorderapi.domain.exceptions.MesaExistingException;
import com.deiz0n.makeorderapi.domain.exceptions.MesaNotFoundException;
import com.deiz0n.makeorderapi.repositories.MesaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MesaService {

    private MesaRepository repository;
    private ModelMapper mapper;

    public MesaService(MesaRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<MesaDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mesa -> mapper.map(mesa, MesaDTO.class))
                .collect(Collectors.toList());
    }

    public MesaDTO getById(UUID id) {
        return repository.findById(id)
                .map(mesa -> mapper.map(mesa, MesaDTO.class))
                .orElseThrow(() -> new MesaNotFoundException("Não foi possível encontrar uma mesa com o id informado"));
    }

    public MesaDTO create(Mesa newMesa) {
        if (repository.findByNumero(newMesa.getNumero()).isPresent())
            throw new MesaExistingException("Mesa já cadastrada");

        var mesa = repository.save(newMesa);
        return mapper.map(mesa, MesaDTO.class);
    }

    public void delete(UUID id) {
        var mesa = getById(id);

        try {
            repository.deleteById(mesa.getId());
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("A mesa não pode ser excluida por está vinculada com um pedido");
        }
    }
}
