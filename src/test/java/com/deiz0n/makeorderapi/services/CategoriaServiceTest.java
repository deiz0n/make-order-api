package com.deiz0n.makeorderapi.services;

import com.deiz0n.makeorderapi.domain.dtos.CategoriaDTO;
import com.deiz0n.makeorderapi.domain.entities.Categoria;
import com.deiz0n.makeorderapi.domain.exceptions.CategoriaExistingException;
import com.deiz0n.makeorderapi.domain.exceptions.CategoriaNotFoundException;
import com.deiz0n.makeorderapi.repositories.CategoriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class CategoriaServiceTest {

    public static final Integer INDEX = 0;
    public static final UUID ID = UUID.randomUUID();
    public static final String NOME = "Categoria";

    @InjectMocks
    private CategoriaService service;
    @Mock
    private CategoriaRepository repository;
    @Mock
    private ModelMapper mapper;

    private Categoria categoria;
    private CategoriaDTO categoriaDTO;
    private Optional<Categoria> optional;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockData();
    }

    @Test
    void whenGetAllThenReturnListOfCategoriaDTO() {
        when(repository.findAll()).thenReturn(List.of(categoria));
        when(mapper.map(any(), any())).thenReturn(categoriaDTO);

        List<CategoriaDTO> responseList = service.getAll();

        assertNotNull(responseList);
        assertEquals(1, responseList.size());
        assertEquals(CategoriaDTO.class, responseList.get(INDEX).getClass());
        assertEquals(ArrayList.class, responseList.getClass());

        assertEquals(ID, responseList.get(INDEX).getId());
        assertEquals(NOME, responseList.get(INDEX).getNome());
    }

    @Test
    void whenGetByIdThenReturnCategoriaDTO() {
        when(repository.findById(any(UUID.class))).thenReturn(optional);
        when(mapper.map(any(), any())).thenReturn(categoriaDTO);

        CategoriaDTO response = service.getById(ID);

        assertNotNull(response);
        assertEquals(CategoriaDTO.class, response.getClass());

        assertEquals(ID, response.getId());
        assertEquals(NOME, response.getNome());
    }

    @Test
    void whenGetByIdThenThrowCategoriaNotFoundException() {
        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());

        try {
            CategoriaDTO response = service.getById(ID);
        } catch (Exception e) {
            assertEquals(e.getClass(), CategoriaNotFoundException.class);
            assertEquals("Não foi possível encontrar uma categoria com o Id informado", e.getMessage() );
        }

    }

    @Test
    void whenCreateThenReturnCategoriaDTO() {
        when(repository.findByNome(anyString())).thenReturn(Optional.empty());
        when(mapper.map(any(), any())).thenReturn(categoria);
        when(repository.save(any())).thenReturn(categoria);

        CategoriaDTO response = service.create(categoriaDTO);

        assertNotNull(response);
        assertEquals(CategoriaDTO.class, response.getClass());

        assertEquals(ID, response.getId());
        assertEquals(NOME, response.getNome());
    }

    @Test
    void whenCreateThenThrowCategoriaExistingException() {
        when(repository.findByNome(any())).thenReturn(optional);
        when(repository.save(any())).thenReturn(categoria);
        when(mapper.map(any(), any())).thenReturn(categoriaDTO);

        try {
            CategoriaDTO responnse = service.create(categoriaDTO);
        } catch (Exception e) {
            assertEquals(CategoriaExistingException.class, e.getClass());
            assertEquals("Categoria já cadastrada",e.getMessage());
        }
    }

    @Test
    void whenDeleteThenDontReturn() {
        when(repository.findById(any(UUID.class))).thenReturn(optional);
        when(mapper.map(any(), any())).thenReturn(categoriaDTO);

        doNothing().when(repository).deleteById(any(UUID.class));

        service.delete(ID);

        verify(repository, times(1)).deleteById(any(UUID.class));
    }

    @Test
    void whenDeleteThenThrowCategoriaNotFoundException() {
        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());
        when(mapper.map(any(), any())).thenReturn(categoriaDTO);

        try {
            service.delete(ID);
        } catch (Exception e) {
            assertEquals(CategoriaNotFoundException.class, e.getClass());
            assertEquals("Não foi possível encontrar uma categoria com o Id informado", e.getMessage());
        }
    }

    public void mockData() {
        categoria = new Categoria(ID, NOME);
        categoriaDTO = new CategoriaDTO(ID, NOME);
        optional = Optional.of(categoria);
    }
}
