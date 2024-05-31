package com.deiz0n.makeorderapi.services;

import com.deiz0n.makeorderapi.domain.entities.Item;
import com.deiz0n.makeorderapi.domain.entities.ItensPedido;
import com.deiz0n.makeorderapi.domain.entities.Pedido;
import com.deiz0n.makeorderapi.domain.event.ItensPedidoEvent;
import com.deiz0n.makeorderapi.repositories.ItensPedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ItensPedidoServiceTest {

    public static final UUID ID = UUID.randomUUID();
    public static final Integer QUANTIDADE = 10;

    @InjectMocks
    private ItensPedidoService service;
    @Mock
    private ItensPedidoRepository itensPedidoRepository;
    @Mock
    private ApplicationEventPublisher eventPublisher;

    private ItensPedido itensPedido;
    private ItensPedidoEvent eventCreated;
    private ItensPedidoEvent eventUpdated;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockData();
    }

    @Test
    void listenerCreatedItensPedido() {
        when(itensPedidoRepository.save(any())).thenReturn(itensPedido);
        doNothing().when(eventPublisher).publishEvent(any());

        service.createListener(eventCreated);

        verify(itensPedidoRepository, times(1)).save(any());
    }

    @Test
    void listenerUpdatedItensPedido() {
        when(itensPedidoRepository.save(any())).thenReturn(itensPedido);
        doNothing().when(eventPublisher).publishEvent(any());

        service.createListener(eventUpdated);

        verify(itensPedidoRepository, times(1)).save(any());
    }

    private void mockData() {
        itensPedido = new ItensPedido(ID, QUANTIDADE, new Item(), new Pedido());
        eventCreated = new ItensPedidoEvent(this, itensPedido);
        eventUpdated = new ItensPedidoEvent(this, itensPedido, ID);
    }


}