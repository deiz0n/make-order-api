package com.deiz0n.makeorderapi.services;

import com.deiz0n.makeorderapi.domain.events.ComandaCreatedEvent;
import com.deiz0n.makeorderapi.repositories.ComandaRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ComandaService {

    private ComandaRepository repository;

    public ComandaService(ComandaRepository repository) {
        this.repository = repository;
    }

    @EventListener
    public void create(ComandaCreatedEvent event) {
        repository.save(event.getComanda());
    }

}
