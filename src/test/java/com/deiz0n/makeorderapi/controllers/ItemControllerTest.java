package com.deiz0n.makeorderapi.controllers;

import com.deiz0n.makeorderapi.domain.dtos.CategoriaDTO;
import com.deiz0n.makeorderapi.domain.dtos.ItemDTO;
import com.deiz0n.makeorderapi.domain.entities.Categoria;
import com.deiz0n.makeorderapi.domain.entities.Item;
import com.deiz0n.makeorderapi.domain.utils.responses.ResponseRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {

    private static final UUID ID = UUID.randomUUID();
    private static final String NOME = "Item 1";
    private static final BigDecimal PRECO = BigDecimal.valueOf(12.5);
    private static final String DESCRICAO = "Descrição 1";
    private static final Integer QUANTIDADE_DISPONIVEL = 1;

    @MockBean
    private ItemController controller;
    @Autowired
    private MockMvc mockMvc;

    private ItemDTO itemDTO;
    private ResponseRequest responseRequest;

    @BeforeEach
    void setUp() {
        mockData();
    }

    @Test
    void whenGetItemThenReturnHttpOk() throws Exception {
        when(controller.getItem(any(UUID.class))).thenReturn(ResponseEntity.ok(itemDTO));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v2.0/itens/{id}", ID)
                .accept(MediaType.APPLICATION_JSON)
                    .with(csrf())
                    .with(user("dudu")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.nome").value(itemDTO.getNome()))
                .andExpect(jsonPath("$.preco").value(itemDTO.getPreco().toString()))
                .andExpect(jsonPath("$.descricao").value(itemDTO.getDescricao()))
                .andExpect(jsonPath("$.quantidade_disponivel").value(itemDTO.getQuantidadeDisponivel()));
    }

    @Test
    void whenGetTopItensThenReturnHttpOk() throws Exception {
        when(controller.getTopItens()).thenReturn(ResponseEntity.ok(List.of()));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v2.0/itens/top-sales")
                .accept(MediaType.APPLICATION_JSON)
                    .with(csrf())
                    .with(user("dudu")))
                .andExpect(status().isOk());
    }

    @Test
    void whenCreateItemThenReturnHttpCreated() throws Exception {
        when(controller.createItem(any())).thenReturn(ResponseEntity.created(any(URI.class)).body(itemDTO));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v2.0/itens/create")
                .accept(MediaType.APPLICATION_JSON)
                    .with(csrf())
                    .with(user("dudu"))
                    .content(toJson(new Item(ID, NOME, PRECO, DESCRICAO, QUANTIDADE_DISPONIVEL, new Categoria(), List.of())))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.nome").value(itemDTO.getNome()))
                .andExpect(jsonPath("$.preco").value(itemDTO.getPreco().toString()))
                .andExpect(jsonPath("$.descricao").value(itemDTO.getDescricao()))
                .andExpect(jsonPath("$.quantidade_disponivel").value(itemDTO.getQuantidadeDisponivel()));
    }

    @Test
    void whenDeleteItemThenReturnHttpNoContent() throws Exception {
        when(controller.deleteItem(any())).thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseRequest));

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v2.0/itens/delete/{id}", ID)
                .accept(MediaType.APPLICATION_JSON)
                    .with(csrf())
                    .with(user("dudu")))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.instant").isNotEmpty())
                .andExpect(jsonPath("$.title").value(responseRequest.getTitle()))
                .andExpect(jsonPath("$.description").value(responseRequest.getDescription()))
                .andExpect(jsonPath("$.status").value(responseRequest.getStatus()));
    }

    @Test
    void whenUpdateItemThenReturnHttpOk() throws Exception {
        when(controller.updateItem(any(UUID.class), any())).thenReturn(ResponseEntity.ok(itemDTO));

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/v2.0/itens/update/{id}", ID)
                .accept(MediaType.APPLICATION_JSON)
                    .with(csrf())
                    .with(user("dudu"))
                    .content(toJson(new Item(ID, NOME, PRECO, DESCRICAO, QUANTIDADE_DISPONIVEL, new Categoria(), List.of())))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.nome").value(itemDTO.getNome()))
                .andExpect(jsonPath("$.preco").value(itemDTO.getPreco().toString()))
                .andExpect(jsonPath("$.descricao").value(itemDTO.getDescricao()))
                .andExpect(jsonPath("$.quantidade_disponivel").value(itemDTO.getQuantidadeDisponivel()));
    }

    private void mockData() {
        itemDTO = new ItemDTO(
                ID,
                NOME,
                PRECO,
                DESCRICAO,
                QUANTIDADE_DISPONIVEL,
                new CategoriaDTO()
        );
        responseRequest = new ResponseRequest(
                Instant.now(),
                "Recurso excluído",
                String.format("O item com id: {%s} foi excluído com sucesso", ID.toString()),
                HttpStatus.NO_CONTENT.value()
        );
    }

    private String toJson(Object item) {
        try {
            return new ObjectMapper().writeValueAsString(item);
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }
}