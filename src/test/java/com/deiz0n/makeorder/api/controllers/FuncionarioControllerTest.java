package com.deiz0n.makeorder.api.controllers;

import com.deiz0n.makeorder.domain.dtos.FuncionarioDTO;
import com.deiz0n.makeorder.domain.models.Funcionario;
import com.deiz0n.makeorder.domain.models.Pedido;
import com.deiz0n.makeorder.domain.models.Permissao;
import com.deiz0n.makeorder.domain.services.FuncionarioService;
import org.apache.coyote.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class FuncionarioControllerTest {

    public static final UUID ID = UUID.randomUUID();
    public static final String NOME = "Eduardo";
    public static final String CPF = "123456789";
    public static final String EMAIL = "eduardo@gmail.com";
    public static final String SENHA = "123";
    public static final Date DATA = Date.from(Instant.now());
    public static final String CARGO = "Garçom";
    public static final int INDEX = 0;

    @InjectMocks
    private FuncionarioController controller;
    @Mock
    private FuncionarioService service;

    private Funcionario funcionario;
    private FuncionarioDTO funcionarioDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startFuncionario();
    }

    @Test
    void whenGetFuncionariosThenReturnOk() {
        when(service.getResouces()).thenReturn(List.of(funcionarioDTO));

        ResponseEntity<List<FuncionarioDTO>> response = controller.getFuncionarios();

        assertNotNull(response);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(FuncionarioDTO.class, response.getBody().get(INDEX).getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(ID, response.getBody().get(INDEX).getId());
        assertEquals(NOME, response.getBody().get(INDEX).getNome());
        assertEquals(CPF, response.getBody().get(INDEX).getCpf());
        assertEquals(SENHA, response.getBody().get(INDEX).getSenha());
        assertEquals(DATA, response.getBody().get(INDEX).getDataNascimento());
        assertEquals(CARGO, response.getBody().get(INDEX).getCargo());
    }

    @Test
    void whenCreateFuncionarioThenReturnCreated() {
        when(service.createResource(any())).thenReturn(funcionarioDTO);

        ResponseEntity<FuncionarioDTO> response = controller.createFuncionario(funcionarioDTO);

        assertNotNull(response);
        assertNotNull(response.getHeaders().get("Location"));

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(FuncionarioDTO.class, response.getBody().getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NOME, response.getBody().getNome());
        assertEquals(CPF, response.getBody().getCpf());
        assertEquals(SENHA, response.getBody().getSenha());
        assertEquals(DATA, response.getBody().getDataNascimento());
        assertEquals(CARGO, response.getBody().getCargo());
    }

    private void startFuncionario() {
        funcionario = new Funcionario(ID
                ,NOME
                ,CPF
                ,EMAIL
                ,SENHA
                ,DATA
                ,CARGO
                ,List.of(new Pedido())
                ,List.of(new Permissao()));
        funcionarioDTO = new FuncionarioDTO(ID
                ,NOME
                ,CPF
                ,EMAIL
                ,SENHA
                ,DATA
                ,CARGO);
    }

}