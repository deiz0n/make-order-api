package com.deiz0n.makeorderapi.controllers;

import com.deiz0n.makeorderapi.domain.dtos.CategoriaDTO;
import com.deiz0n.makeorderapi.domain.entities.Categoria;
import com.deiz0n.makeorderapi.services.CategoriaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class CategoriaControllerTest {

    public static final Integer INDEX = 0;
    public static final UUID ID = UUID.randomUUID();
    public static final String NOME = "Categoria 1";

    @InjectMocks
    private CategoriaController controller;
    @Mock
    private CategoriaService service;

    private Categoria categoria;
    private CategoriaDTO categoriaDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockData();
    }

    @Test
    void whenGetCategoriasThenReturnHttpOk() {
        when(service.getAll()).thenReturn(List.of(categoriaDTO));

        ResponseEntity<List<CategoriaDTO>> responseList = controller.getCategorias();

        assertNotNull(responseList);
        assertNotNull(responseList.getBody());

        assertEquals(ResponseEntity.class, responseList.getClass());
        assertEquals(HttpStatus.OK, responseList.getStatusCode());

        assertEquals(ID, responseList.getBody().get(INDEX).getId());
        assertEquals(NOME, responseList.getBody().get(INDEX).getNome());
    }

    @Test
    void whenGetCategoriaThenReturnHttpOk() {
        when(service.getById(any(UUID.class))).thenReturn(categoriaDTO);

        ResponseEntity<CategoriaDTO> response = controller.getCategoria(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NOME, response.getBody().getNome());
    }

    @Test
    void whenCreateCategoriaThenReturnHttpCreated() {
        when(service.create(any())).thenReturn(categoriaDTO);

        ResponseEntity<CategoriaDTO> response = controller.createCategoria(categoria);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotNull(response.getHeaders().get("Location"));

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NOME, response.getBody().getNome());
    }

    @Test
    void whenDeleteCategoriaThenReturnHttpNoContent() {
        doNothing().when(service).delete(any(UUID.class));

        ResponseEntity<?> response = controller.deleteCategoria(ID);

        assertNotNull(response);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(service, times(1)).delete(any(UUID.class));
    }

    public void mockData() {
        categoria = new Categoria(ID, NOME);
        categoriaDTO = new CategoriaDTO(ID, NOME);
    }
}