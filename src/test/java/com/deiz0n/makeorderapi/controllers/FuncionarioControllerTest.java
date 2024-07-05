package com.deiz0n.makeorderapi.controllers;

import com.deiz0n.makeorderapi.domain.dtos.FuncionarioDTO;
import com.deiz0n.makeorderapi.domain.entities.Funcionario;
import com.deiz0n.makeorderapi.domain.entities.Pedido;
import com.deiz0n.makeorderapi.domain.enums.Setor;
import com.deiz0n.makeorderapi.domain.utils.responses.ResponseRequest;
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
import java.util.Date;
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
class FuncionarioControllerTest<T> {


    public static final String NOME = "Nome";
    public static final String EMAIL = "email@test.com";
    public static final Date DATA_NASCIMENTO = Date.from(Instant.now());
    public static final Setor SETOR = Setor.COZINHA;
    public static final UUID ID = UUID.randomUUID();
    private static final String SENHA = "senha";
    private static final String CPF = "97043405084";
    private static final UUID SUB_ID = UUID.randomUUID();
    private static final String DUDU = "dudu";

    @MockBean
    private FuncionarioController controller;
    @Autowired
    private MockMvc mockMvc;

    private FuncionarioDTO funcionarioDTO;
    private ResponseRequest responseRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockData();
    }

    @Test
    void whenGetFuncionariosThenReturnHttpOk() throws Exception {
        when(controller.getFuncionarios()).thenReturn(ResponseEntity.ok(List.of(funcionarioDTO)));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v2.0/funcionarios")
                .accept(MediaType.APPLICATION_JSON)
                    .with(csrf())
                    .with(user(DUDU)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].id").value(ID.toString()))
                .andExpect(jsonPath("$[0].nome").value(NOME))
                .andExpect(jsonPath("$[0].email").value(EMAIL))
                .andExpect(jsonPath("$[0].data_nascimento").isNotEmpty())
                .andExpect(jsonPath("$[0].setor").value(SETOR.toString()));
    }

    @Test
    void whenGetFuncionarioThenReturnHttpOk() throws Exception {
        when(controller.getFuncionario(any(UUID.class))).thenReturn(ResponseEntity.ok(funcionarioDTO));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v2.0/funcionarios/{id}", ID)
                .accept(MediaType.APPLICATION_JSON)
                    .with(csrf())
                    .with(user(DUDU)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.id").value(ID.toString()))
                .andExpect(jsonPath("$.nome").value(NOME))
                .andExpect(jsonPath("$.email").value(EMAIL))
                .andExpect(jsonPath("$.data_nascimento").isNotEmpty())
                .andExpect(jsonPath("$.setor").value(SETOR.toString()));
    }

    @Test
    void whenGetTopFuncionariosThenReturnOk() throws Exception {
        when(controller.getTopFuncionarios()).thenReturn(ResponseEntity.ok(List.of()));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v2.0/funcionarios/top-sales")
                .accept(MediaType.APPLICATION_JSON)
                    .with(csrf())
                    .with(user(DUDU)))
                .andExpect(status().isOk());
    }

    @Test
    void whenCreateFuncionarioThenReturnHttpCreated() throws Exception {
        when(controller.createFuncionario(any())).thenReturn(ResponseEntity.created(any(URI.class)).body(funcionarioDTO));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v2.0/funcionarios/create")
                .accept(MediaType.APPLICATION_JSON)
                    .with(csrf())
                    .with(user(DUDU))
                    .content(
                            toJson(
                                    new Funcionario(ID, NOME, CPF, EMAIL, SENHA, DATA_NASCIMENTO, SETOR, SUB_ID, List.of(new Pedido()))))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.id").value(ID.toString()))
                .andExpect(jsonPath("$.nome").value(NOME))
                .andExpect(jsonPath("$.email").value(EMAIL))
                .andExpect(jsonPath("$.data_nascimento").isNotEmpty())
                .andExpect(jsonPath("$.setor").value(SETOR.toString()));
    }

    @Test
    void whenDeleteFuncionarioThenReturnHttpNoContent() throws Exception {
        when(controller.deleteFuncionario(any(UUID.class))).thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseRequest));

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v2.0/funcionarios/delete/{id}", ID)
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

    @Test
    void whenUpdateFuncionarioThenReturnHttoOk() throws Exception {
        when(controller.updateFuncionario(any(UUID.class), any())).thenReturn(ResponseEntity.ok(funcionarioDTO));

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/v2.0/funcionarios/update/{id}", ID)
                .accept(MediaType.APPLICATION_JSON)
                    .with(csrf())
                        .with(user(DUDU))
                        .content(
                                toJson(
                                        new Funcionario(ID, NOME, CPF, EMAIL, SENHA, DATA_NASCIMENTO, SETOR, SUB_ID, List.of(new Pedido()))))
                        .contentType((MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.id").value(ID.toString()))
                .andExpect(jsonPath("$.nome").value(NOME))
                .andExpect(jsonPath("$.email").value(EMAIL))
                .andExpect(jsonPath("$.data_nascimento").isNotEmpty())
                .andExpect(jsonPath("$.setor").value(SETOR.toString()));
    }

    private void mockData() {
        funcionarioDTO = new FuncionarioDTO(
                ID,
                NOME,
                EMAIL,
                DATA_NASCIMENTO,
                SETOR
        );
        responseRequest = new ResponseRequest(
                Instant.now(),
                "Recurso excluído",
                String.format("O funcionário com id: {%s} foi excluído com sucesso", ID.toString()),
                HttpStatus.NO_CONTENT.value()
        );
    }

    private String toJson(Object funcionario) {
        try {
            return new ObjectMapper().writeValueAsString(funcionario);
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }
}