package com.deiz0n.makeorderapi.controllers;

import com.deiz0n.makeorderapi.domain.dtos.CategoriaDTO;
import com.deiz0n.makeorderapi.domain.entities.Categoria;
import com.deiz0n.makeorderapi.domain.utils.responses.ResponseRequest;
import com.deiz0n.makeorderapi.services.CategoriaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CategoriaControllerTest {

    public static final UUID ID = UUID.randomUUID();
    public static final String NOME = "Categoria 1";
    public static final String DUDU = "dudu";

    @MockBean
    private CategoriaController controller;
    @MockBean
    private CategoriaService service;
    @Autowired
    private MockMvc mockMvc;

    private CategoriaDTO categoriaDTO;
    private ResponseRequest responseRequest;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockData();
    }

    @Test
    void whenGetCategoriasThenReturnHttpOk() throws Exception {
        when(controller.getCategorias()).thenReturn(ResponseEntity.ok(List.of(categoriaDTO)));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v2.0/categorias")
                .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .with(user(DUDU)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").isNotEmpty())
                .andExpect(jsonPath("$[0].id").value(ID.toString()))
                .andExpect(jsonPath("$[0].nome").value(NOME));
    }

    @Test
    void whenGetCategoriaThenReturnHttpOk() throws Exception {
        when(controller.getCategoria(any(UUID.class))).thenReturn(ResponseEntity.ok(categoriaDTO));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v2.0/categorias/{id}", ID)
                .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .with(user(DUDU)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.id").value(ID.toString()))
                .andExpect(jsonPath("$.nome").value(NOME));

    }

    @Test
    void whenCreateCategoriaThenReturnHttpCreated() throws Exception {
        when(controller.createCategoria(any())).thenReturn(ResponseEntity.created(any(URI.class)).body(categoriaDTO));

        mockMvc.perform( MockMvcRequestBuilders
                .post("/api/v2.0/categorias/create")
                .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .with(user(DUDU))
                        .content(toJson(new Categoria(null, "Categoria")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.id").value(ID.toString()))
                .andExpect(jsonPath("$.nome").value(NOME));
    }

    @Test
    void whenDeleteCategoriaThenReturnHttpNoContent() throws Exception {
        when(controller.deleteCategoria(any(UUID.class))).thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseRequest));

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v2.0/categorias/delete/{id}", ID)
                .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .with(user(DUDU)))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.instant").isNotEmpty())
                .andExpect(jsonPath("$.title").value(responseRequest.getTitle()))
                .andExpect(jsonPath("$.description").value(responseRequest.getDescription()))
                .andExpect(jsonPath("$.status").value(responseRequest.getStatus().toString()));
    }

    private void mockData() {
        categoriaDTO = new CategoriaDTO(ID, NOME);
        responseRequest = new ResponseRequest(
                Instant.now(),
                "Recurso excluído",
                String.format("A categoria com id: {%s} foi excluída com sucesso", ID.toString()),
                HttpStatus.NO_CONTENT.value());
    }

    private String toJson(Object categoria) {
        try {
            return new ObjectMapper().writeValueAsString(categoria);
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }
}