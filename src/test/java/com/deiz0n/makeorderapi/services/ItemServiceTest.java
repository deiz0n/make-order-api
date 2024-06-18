package com.deiz0n.makeorderapi.services;

import com.deiz0n.makeorderapi.domain.dtos.CategoriaDTO;
import com.deiz0n.makeorderapi.domain.dtos.ItemDTO;
import com.deiz0n.makeorderapi.domain.entities.Categoria;
import com.deiz0n.makeorderapi.domain.entities.Item;
import com.deiz0n.makeorderapi.domain.exceptions.ItemNotFoundException;
import com.deiz0n.makeorderapi.repositories.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ItemServiceTest {

    public static final UUID ID = UUID.randomUUID();
    public static final String NOME = "Item 1";
    public static final BigDecimal PRECO = new BigDecimal("18.2");
    public static final String DESCRICAO = "Descrição 1";
    public static final int QUANTIDADE_DISPONIVEL = 4;
    public static final int INDEX = 0;

    @InjectMocks
    private ItemService itemService;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private ModelMapper mapper;

    private Item item;
    private ItemDTO itemDTO;
    private Optional<Item> optional;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockData();
    }

    @Test
    void whenGetAllThenReturnListOfItemDTO() {
        when(itemRepository.findAll()).thenReturn(List.of(item));
        when(mapper.map(any(), any())).thenReturn(itemDTO);

        List<ItemDTO> responseList = itemService.getAll();

        assertNotNull(responseList);
        assertEquals(1, responseList.size());
        assertEquals(ArrayList.class, responseList.getClass());
        assertEquals(ItemDTO.class, responseList.get(INDEX).getClass());

        assertEquals(ID, responseList.get(INDEX).getId());
        assertEquals(NOME, responseList.get(INDEX).getNome());
        assertEquals(PRECO, responseList.get(INDEX).getPreco());
        assertEquals(DESCRICAO, responseList.get(INDEX).getDescricao());
        assertEquals(QUANTIDADE_DISPONIVEL, responseList.get(INDEX).getQuantidadeDisponivel());
    }

    @Test
    void whenGetByIdThenReturnItemDTO() {
        when(itemRepository.findById(any(UUID.class))).thenReturn(optional);
        when(mapper.map(any(), any())).thenReturn(itemDTO);

        ItemDTO response = itemService.getById(ID);

        assertNotNull(response);
        assertEquals(ItemDTO.class, response.getClass());

        assertEquals(ID, response.getId());
        assertEquals(NOME, response.getNome());
        assertEquals(PRECO, response.getPreco());
        assertEquals(DESCRICAO, response.getDescricao());
        assertEquals(QUANTIDADE_DISPONIVEL, response.getQuantidadeDisponivel());
    }

    @Test
    void whenGetByIdThenThrowItemNotFoundException() {
        when(itemRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        var exception = assertThrows(
                ItemNotFoundException.class,
                () -> itemService.getById(ID)
        );

        assertEquals("Não foi possível encontrar um item com o Id informado", exception.getMessage());
    }

    @Test
    void whenCreateThenReturnItemDTO() {
        when(itemRepository.save(any())).thenReturn(item);
        when(mapper.map(any(), any())).thenReturn(itemDTO);

        ItemDTO response = itemService.create(item);

        assertNotNull(response);
        assertEquals(ItemDTO.class, response.getClass());

        assertEquals(ID, response.getId());
        assertEquals(NOME, response.getNome());
        assertEquals(PRECO, response.getPreco());
        assertEquals(DESCRICAO, response.getDescricao());
        assertEquals(QUANTIDADE_DISPONIVEL, response.getQuantidadeDisponivel());
    }

    @Test
    void whenDeleteThenDontReturn() {
        when(itemRepository.findById(any(UUID.class))).thenReturn(optional);
        when(mapper.map(any(), any())).thenReturn(itemDTO);

        doNothing().when(itemRepository).deleteById(any(UUID.class));

        itemService.delete(ID);

        verify(itemRepository, times(1)).deleteById(any(UUID.class));
    }

    @Test
    void whenDeleteThenThrowItemNotFoundException() {
        when(itemRepository.findById(any())).thenReturn(Optional.empty());

        var exception = assertThrows(
                ItemNotFoundException.class,
                () -> itemService.getById(ID)
        );

        assertEquals("Não foi possível encontrar um item com o Id informado", exception.getMessage());
    }

    @Test
    void whenUpdateThenReturnItemDTO() {
        when(itemRepository.getReferenceById(any(UUID.class))).thenReturn(item);
        when(itemRepository.save(any())).thenReturn(item);
        when(mapper.map(any(), any())).thenReturn(itemDTO);

        ItemDTO response = itemService.update(ID, item);

        assertNotNull(response);
        assertEquals(ItemDTO.class, response.getClass());

        assertEquals(ID, response.getId());
        assertEquals(NOME, response.getNome());
        assertEquals(PRECO, response.getPreco());
        assertEquals(DESCRICAO, response.getDescricao());
        assertEquals(QUANTIDADE_DISPONIVEL, response.getQuantidadeDisponivel());
    }

    @Test
    void whenUpdateThenThrowItemNotFoundException() {
        when(itemRepository.getReferenceById(any(UUID.class))).thenReturn(null);
        when(itemRepository.save(any())).thenReturn(item);

        var exception = assertThrows(
                ItemNotFoundException.class,
                () -> itemService.update(ID, item)
        );

        assertEquals("Não foi possível encontrar um item com o Id informado", exception.getMessage());
    }

    private void mockData() {
        item = new Item(
                ID,
                NOME,
                PRECO,
                DESCRICAO,
                QUANTIDADE_DISPONIVEL,
                new Categoria(),
                List.of());
        itemDTO = new ItemDTO(
                ID,
                NOME,
                PRECO,
                DESCRICAO,
                QUANTIDADE_DISPONIVEL,
                new CategoriaDTO());
        optional = Optional.of(item);
    }
}