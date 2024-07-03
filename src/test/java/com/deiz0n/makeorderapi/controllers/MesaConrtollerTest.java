package com.deiz0n.makeorderapi.controllers;

import com.deiz0n.makeorderapi.domain.dtos.MesaDTO;
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

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MesaConrtollerTest {

    private static final UUID ID = UUID.randomUUID();
    @MockBean
    private MesaConrtoller conrtoller;
    @Autowired
    private MockMvc mockMvc;

    private MesaDTO mesaDTO;
    private ResponseRequest responseRequest;

    @BeforeEach
    void setUp(){
        mockData();
    }

    @Test
    void whenGetMesasThenReturnHttpOk() throws Exception {
        when(conrtoller.getMesas()).thenReturn(ResponseEntity.ok(List.of(mesaDTO)));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v2.0/mesas")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .with(user("dudu")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].id").value(mesaDTO.getId().toString()))
                .andExpect(jsonPath("$[0].numero").value(mesaDTO.getNumero()))
                .andExpect(jsonPath("$[0].client").value(mesaDTO.getClient()));
    }

    @Test
    void whenGetMesaThenReturnHttpOk() throws Exception {
        when(conrtoller.getMesa(any(UUID.class))).thenReturn(ResponseEntity.ok(mesaDTO));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v2.0/mesas/{id}", ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .with(user("dudu")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.id").value(mesaDTO.getId().toString()))
                .andExpect(jsonPath("$.numero").value(mesaDTO.getNumero()))
                .andExpect(jsonPath("$.client").value(mesaDTO.getClient()));
    }

    @Test
    void whenCreateMesaThenReturnHttpCreated() throws Exception {
        when(conrtoller.createMesa(any())).thenReturn(ResponseEntity.created(any(URI.class)).body(mesaDTO));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v2.0/mesas/create")
                .accept(MediaType.APPLICATION_JSON)
                    .with(csrf())
                    .with(user("dudu"))
                    .content(toJson(new MesaDTO(UUID.randomUUID(), 1, "Cliente 1")))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.id").value(mesaDTO.getId().toString()))
                .andExpect(jsonPath("$.numero").value(mesaDTO.getNumero()))
                .andExpect(jsonPath("$.client").value(mesaDTO.getClient()));
    }

    @Test
    void whenDeleteMesaThenReturnHttpNoContent() throws Exception {
        when(conrtoller.deleteMesa(any(UUID.class))).thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseRequest));

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v2.0/mesas/delete/{id}", ID)
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

    private void mockData() {
        mesaDTO = new MesaDTO(
                ID,
                1,
                "Cliente 1"
        );
        responseRequest = new ResponseRequest(
                Instant.now(),
                "Recurso excluído",
                String.format("O item com id: {%s} foi excluído com sucesso", ID.toString()),
                HttpStatus.NO_CONTENT.value()
        );
    }

    private String toJson(Object mesa) {
        try {
            return new ObjectMapper().writeValueAsString(mesa);
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }
}