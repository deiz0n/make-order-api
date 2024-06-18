package com.deiz0n.makeorderapi.services;

import com.deiz0n.makeorderapi.domain.entities.Comanda;
import com.deiz0n.makeorderapi.domain.events.CreatedComandaEvent;
import com.deiz0n.makeorderapi.repositories.ComandaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ComandaServiceTest {

    @InjectMocks
    private ComandaService service;
    @Mock
    private ComandaRepository repository;
    @Mock
    private ApplicationEventPublisher eventPublisher;

    private Comanda comanda;
    private CreatedComandaEvent event;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockData();
    }

    @Test
    void listenerCreatedComanda() {
        when(repository.save(any())).thenReturn(comanda);

        doNothing().when(eventPublisher).publishEvent(any());

        service.create(event);

        verify(repository, times(1)).save(any());
    }

    private void mockData() {
        comanda = new Comanda(UUID.randomUUID(), List.of());
        event = new CreatedComandaEvent(this, comanda);
    }
}