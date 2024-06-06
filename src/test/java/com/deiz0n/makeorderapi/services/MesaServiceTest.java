package com.deiz0n.makeorderapi.services;

import com.deiz0n.makeorderapi.domain.dtos.MesaDTO;
import com.deiz0n.makeorderapi.domain.entities.Mesa;
import com.deiz0n.makeorderapi.domain.exceptions.MesaExistingException;
import com.deiz0n.makeorderapi.domain.exceptions.MesaNotFoundException;
import com.deiz0n.makeorderapi.repositories.MesaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class MesaServiceTest {

    public static final Integer INDEX = 0;
    public static final UUID ID = UUID.randomUUID();
    public static final Integer NUMERO = 1;
    public static final String CLIENTE = "Cliente 1";

    @InjectMocks
    private MesaService service;
    @Mock
    private MesaRepository repository;

    private Mesa mesa;
    private MesaDTO mesaDTO;
    private Optional<Mesa> optional;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockData();
    }

    @Test
    void whenGetAllThenReturnListOfMesaDTO() {
        when(repository.findAll()).thenReturn(List.of(mesa));

        List<MesaDTO> responseList = service.getAll();

        assertNotNull(responseList);
        assertEquals(ArrayList.class, responseList.getClass());
        assertEquals(MesaDTO.class, responseList.get(INDEX).getClass());

        assertEquals(ID, responseList.get(INDEX).id());
        assertEquals(NUMERO, responseList.get(INDEX).numero());
        assertEquals(CLIENTE, responseList.get(INDEX).cliente());
    }

    @Test
    void whenGetByThenReturnMesaDTO() {
        when(repository.findById(any(UUID.class))).thenReturn(optional);

        MesaDTO response = service.getById(ID);

        assertNotNull(response);
        assertEquals(MesaDTO.class, response.getClass());

        assertEquals(ID, response.id());
        assertEquals(NUMERO, response.numero());
        assertEquals(CLIENTE, response.cliente());
    }

    @Test
    void whenGetByThenThrowMesaNotFoundException() {
        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());

        var exception = assertThrows(
                MesaNotFoundException.class,
                () -> service.getById(ID)
        );

        assertEquals("Não foi possível encontrar uma mesa com o id informado", exception.getMessage());
    }

    @Test
    void whenCreateThenReturnMesaDTO() {
        when(repository.findByNumero(anyInt())).thenReturn(Optional.empty());
        when(repository.save(any())).thenReturn(mesa);

        MesaDTO response = service.create(mesa);

        assertNotNull(response);
        assertEquals(MesaDTO.class, response.getClass());

        assertEquals(ID, response.id());
        assertEquals(NUMERO, response.numero());
        assertEquals(CLIENTE, response.cliente());
    }

    @Test
    void whenCreateThenThrowMesaExistingException() {
        when(repository.findByNumero(anyInt())).thenReturn(optional);

        var exception = assertThrows(
                MesaExistingException.class,
                () -> service.create(mesa)
        );

        assertEquals("Mesa já cadastrada", exception.getMessage());
    }

    @Test
    void whenDeleteThenDontReturn() {
        when(repository.findById(any(UUID.class))).thenReturn(optional);

        doNothing().when(repository).deleteById(any(UUID.class));

        service.delete(ID);

        verify(repository, times(1)).deleteById(any(UUID.class));
    }

    @Test
    void whenDeleteThenThrowMesaNotFoundException() {
        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());

        var exception = assertThrows(
                MesaNotFoundException.class,
                () -> service.getById(ID)
        );

        assertEquals("Não foi possível encontrar uma mesa com o id informado", exception.getMessage());
    }

    private void mockData() {
        mesa = new Mesa(ID, NUMERO, CLIENTE, List.of());
        mesaDTO = new MesaDTO(ID, NUMERO, CLIENTE);
        optional = Optional.of(mesa);
    }
}