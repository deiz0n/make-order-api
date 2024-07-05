package com.deiz0n.makeorderapi.controllers;

import com.deiz0n.makeorderapi.domain.dtos.FuncionarioDTO;
import com.deiz0n.makeorderapi.domain.dtos.PedidoDTO;
import com.deiz0n.makeorderapi.domain.entities.Comanda;
import com.deiz0n.makeorderapi.domain.entities.Funcionario;
import com.deiz0n.makeorderapi.domain.entities.Mesa;
import com.deiz0n.makeorderapi.domain.entities.Pedido;
import com.deiz0n.makeorderapi.domain.enums.FormaPagamento;
import com.deiz0n.makeorderapi.domain.enums.StatusPedido;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PedidoControllerTest {

    private static final UUID ID = UUID.randomUUID();
    private static final Instant INSTANT = Instant.now();
    private static final FormaPagamento FORMA_PAGAMENTO = FormaPagamento.CREDITO;
    private static final StatusPedido STATUS_PEDIDO = StatusPedido.PENDENTE;
    private static final Integer CODIGO = 1;
    private static final String OBSERVACOES = "Observação 1";

    @MockBean
    private PedidoController controller;
    @Autowired
    private MockMvc mockMvc;

    private PedidoDTO pedidoDTO;
    private ResponseRequest responseRequest;

    @BeforeEach
    void setUp() {
        mockData();
    }

    @Test
    void whenGetPedidosThenReturnHttpOk() throws Exception {
       when(controller.getPedidos()).thenReturn(ResponseEntity.ok(List.of(pedidoDTO)));

       mockMvc.perform(MockMvcRequestBuilders
               .get("/api/v2.0/pedidos")
               .accept(MediaType.APPLICATION_JSON)
                   .with(csrf())
                   .with(user("dudu")))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$").isNotEmpty())
               .andExpect(jsonPath("$[0].id").value(pedidoDTO.getId().toString()))
               .andExpect(jsonPath("$[0].data").isNotEmpty())
               .andExpect(jsonPath("$[0].forma_pagamento").value(pedidoDTO.getFormaPagamento().toString()))
               .andExpect(jsonPath("$[0].status").value(pedidoDTO.getStatus().toString()))
               .andExpect(jsonPath("$[0].codigo").value(pedidoDTO.getCodigo()))
               .andExpect(jsonPath("$[0].observacoes").value(pedidoDTO.getObservacoes()))
               .andExpect(jsonPath("$[0].itens").exists())
               .andExpect(jsonPath("$[0].comanda").exists())
               .andExpect(jsonPath("$[0].funcionario").exists())
               .andExpect(jsonPath("$[0].mesa").exists());
    }

    @Test
    void whenGetPedidoThenReturnHttpOk() throws Exception {
        when(controller.getPedido(any(UUID.class))).thenReturn(ResponseEntity.ok(pedidoDTO));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v2.0/pedidos/{id}", ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .with(user("dudu")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.id").value(pedidoDTO.getId().toString()))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.forma_pagamento").value(pedidoDTO.getFormaPagamento().toString()))
                .andExpect(jsonPath("$.status").value(pedidoDTO.getStatus().toString()))
                .andExpect(jsonPath("$.codigo").value(pedidoDTO.getCodigo()))
                .andExpect(jsonPath("$.observacoes").value(pedidoDTO.getObservacoes()))
                .andExpect(jsonPath("$.itens").exists())
                .andExpect(jsonPath("$.comanda").exists())
                .andExpect(jsonPath("$.funcionario").exists())
                .andExpect(jsonPath("$.mesa").exists());
    }

    @Test
    void whenCreatePedidoThenReturnHttpCreated() throws Exception {
        when(controller.createPedido(any())).thenReturn(ResponseEntity.created(any(URI.class)).body(pedidoDTO));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v2.0/pedidos/create")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .with(user("dudu"))
                        .content(toJson(new Pedido(ID, null, FORMA_PAGAMENTO, null, CODIGO, OBSERVACOES, List.of(), new Comanda(), new Funcionario(), new Mesa())))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.id").value(pedidoDTO.getId().toString()))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.forma_pagamento").value(pedidoDTO.getFormaPagamento().toString()))
                .andExpect(jsonPath("$.status").value(pedidoDTO.getStatus().toString()))
                .andExpect(jsonPath("$.codigo").value(pedidoDTO.getCodigo()))
                .andExpect(jsonPath("$.observacoes").value(pedidoDTO.getObservacoes()))
                .andExpect(jsonPath("$.itens").exists())
                .andExpect(jsonPath("$.comanda").exists())
                .andExpect(jsonPath("$.funcionario").exists())
                .andExpect(jsonPath("$.mesa").exists());
    }

    @Test
    void whenDeletePedidoThenReturnHttpNoContent() throws Exception {
        when(controller.deletePedido(any(UUID.class))).thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseRequest));

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v2.0/pedidos/delete/{id}", ID)
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
    void whenUpdatePedidoThenReturnHttpOk() throws Exception {
        when(controller.updatePedido(any(UUID.class), any())).thenReturn(ResponseEntity.ok(pedidoDTO));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v2.0/pedidos/update/{id}", ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .with(user("dudu"))
                        .content(toJson(new Pedido(ID, null, FORMA_PAGAMENTO, null, CODIGO, OBSERVACOES, List.of(), new Comanda(), new Funcionario(), new Mesa())))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.id").value(pedidoDTO.getId().toString()))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.forma_pagamento").value(pedidoDTO.getFormaPagamento().toString()))
                .andExpect(jsonPath("$.status").value(pedidoDTO.getStatus().toString()))
                .andExpect(jsonPath("$.codigo").value(pedidoDTO.getCodigo()))
                .andExpect(jsonPath("$.observacoes").value(pedidoDTO.getObservacoes()))
                .andExpect(jsonPath("$.itens").exists())
                .andExpect(jsonPath("$.comanda").exists())
                .andExpect(jsonPath("$.funcionario").exists())
                .andExpect(jsonPath("$.mesa").exists());
    }

    @Test
    void whenUpdateStatusThenReturnHttpOk() throws Exception {
        when(controller.updateStatus(any(UUID.class), any())).thenReturn(ResponseEntity.ok(pedidoDTO));

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/v2.0/pedidos/update/status/{id}", ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .with(user("dudu"))
                        .content(toJson(new Pedido(ID, null, FORMA_PAGAMENTO, null, CODIGO, OBSERVACOES, List.of(), new Comanda(), new Funcionario(), new Mesa())))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.id").value(pedidoDTO.getId().toString()))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.forma_pagamento").value(pedidoDTO.getFormaPagamento().toString()))
                .andExpect(jsonPath("$.status").value(pedidoDTO.getStatus().toString()))
                .andExpect(jsonPath("$.codigo").value(pedidoDTO.getCodigo()))
                .andExpect(jsonPath("$.observacoes").value(pedidoDTO.getObservacoes()))
                .andExpect(jsonPath("$.itens").exists())
                .andExpect(jsonPath("$.comanda").exists())
                .andExpect(jsonPath("$.funcionario").exists())
                .andExpect(jsonPath("$.mesa").exists());
    }

    private void mockData() {
        pedidoDTO = new PedidoDTO(
                ID,
                INSTANT,
                FORMA_PAGAMENTO,
                STATUS_PEDIDO,
                CODIGO,
                OBSERVACOES,
                List.of(),
                new Comanda(),
                new FuncionarioDTO(),
                new Mesa()
        );
        responseRequest = new ResponseRequest(
                INSTANT,
                "Recurso excluído",
                String.format("O pedido com id: {%s} foi excluído com sucesso", ID.toString()),
                HttpStatus.NO_CONTENT.value()
        );
    }

    private String toJson(Object request) {
        try {
            return new ObjectMapper().writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }
}