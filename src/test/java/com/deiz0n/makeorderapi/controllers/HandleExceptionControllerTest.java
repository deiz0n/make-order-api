package com.deiz0n.makeorderapi.controllers;

import com.deiz0n.makeorderapi.domain.dtos.FuncionarioDTO;
import com.deiz0n.makeorderapi.domain.entities.Comanda;
import com.deiz0n.makeorderapi.domain.entities.Funcionario;
import com.deiz0n.makeorderapi.domain.entities.Mesa;
import com.deiz0n.makeorderapi.domain.entities.Pedido;
import com.deiz0n.makeorderapi.domain.enums.FormaPagamento;
import com.deiz0n.makeorderapi.domain.enums.Setor;
import com.deiz0n.makeorderapi.domain.exceptions.*;
import com.deiz0n.makeorderapi.domain.utils.requests.RecoveryPasswordRequest;
import com.deiz0n.makeorderapi.domain.utils.requests.ResetPasswordRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.reactive.resource.NoResourceFoundException;

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
class HandleExceptionControllerTest {

    private static final UUID ID = UUID.randomUUID();
    private static final String EMAIL = "email@gmail.com";
    private static final String NOME = "Nome";
    private static final Setor SETOR = Setor.ADMINISTRACAO;
    private static final Date DATA_NASCIMENTO = Date.from(Instant.now());

    @MockBean
    private FuncionarioController funcionarioController;
    @MockBean
    private PedidoController pedidoController;
    @MockBean
    private AuthenticationController authenticationController;
    @Autowired
    private MockMvc mockMvc;

    private FuncionarioDTO funcionarioDTO;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        mockData();
    }

    @Test
    void handleResourceNotFoundThenReturnHttpNotFound() throws Exception {
        when(funcionarioController.getFuncionario(any(UUID.class))).thenThrow(new FuncionarioNotFoundException("Não foi possível encontrar o funcionário com o id informado"));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v2.0/funcionarios/{id}", ID)
                .accept(MediaType.APPLICATION_JSON)
                    .with(csrf())
                    .with(user("dudu")))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.instant").isNotEmpty())
                .andExpect(jsonPath("$.title").value("Recurso não encontrado"))
                .andExpect(jsonPath("$.description").value("Não foi possível encontrar o funcionário com o id informado"))
                .andExpect(jsonPath("$.status").isNotEmpty())
                .andExpect(jsonPath("$.path").isNotEmpty());
    }

    @Test
    void handleResourceExistingExcepionThenReturnHttpConflict() throws Exception {
        when(funcionarioController.createFuncionario(any())).thenThrow(new FuncionarioExistingException("Email já vinculado a um funcionário"));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v2.0/funcionarios/create")
                .accept(MediaType.APPLICATION_JSON)
                    .with(csrf())
                    .with(user("dudu"))
                    .content(toJson(new Funcionario()))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.instant").isNotEmpty())
                .andExpect(jsonPath("$.title").value("Recurso existente"))
                .andExpect(jsonPath("$.description").value("Email já vinculado a um funcionário"))
                .andExpect(jsonPath("$.status").isNotEmpty())
                .andExpect(jsonPath("$.path").isNotEmpty());
    }

    @Test
    void handleResourceIsEmptyExceptionThenReturnHttp() throws Exception {
        when(pedidoController.createPedido(any())).thenThrow(new FuncionarioIsEmptyException("Nenhum funcionário foi vinculado ao pedido"));

        mockMvc.perform(MockMvcRequestBuilders
                    .post("/api/v2.0/pedidos/create")
                    .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .with(user("dudu"))
                        .content(toJson(new Pedido(ID, null, FormaPagamento.CREDITO, null, 1, "", List.of(), new Comanda(), new Funcionario(), new Mesa())))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.instant").isNotEmpty())
                .andExpect(jsonPath("$.title").value("Campo vazio"))
                .andExpect(jsonPath("$.description").value("Nenhum funcionário foi vinculado ao pedido"))
                .andExpect(jsonPath("$.status").isNotEmpty())
                .andExpect(jsonPath("$.path").isNotEmpty());
    }

    @Test
    void handleMethodArgumentNotValidThenReturnHttpConflict() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v2.0/funcionarios/create")
                .accept(MediaType.APPLICATION_JSON)
                    .with(csrf())
                    .with(user("dudu"))
                    .content(
                            toJson(
                                    new Funcionario(ID, NOME, "000000000000", EMAIL, "123", DATA_NASCIMENTO, SETOR, ID, List.of())))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.instant").isNotEmpty())
                .andExpect(jsonPath("$.title").value("Campo inválido"))
                .andExpect(jsonPath("$.description").value("CPF inválido"))
                .andExpect(jsonPath("$.status").isNotEmpty())
                .andExpect(jsonPath("$.path").isNotEmpty());
    }

    @Test
    void handleHttpMessageNotReadableThenReturnHttpBadRequest() throws Exception {
        when(funcionarioController.createFuncionario(any())).thenThrow(new HttpMessageNotReadableException(null, new IllegalArgumentException()));

        mockMvc.perform(MockMvcRequestBuilders
                    .post("/api/v2.0/funcionarios/create")
                    .accept(MediaType.APPLICATION_JSON)
                    .with(csrf())
                    .with(user("dudu"))
                    .content(
                            toJson(
                                    new Funcionario(ID, NOME, "73008149057", EMAIL, "123", DATA_NASCIMENTO, null, ID, List.of())))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.instant").isNotEmpty())
                .andExpect(jsonPath("$.title").value("Formato inválido"))
                .andExpect(jsonPath("$.description").value("O JSON informado possui formato inválido"))
                .andExpect(jsonPath("$.status").isNotEmpty())
                .andExpect(jsonPath("$.path").isNotEmpty());
    }

    @Test
    void handleNoResourceFoundExceptionThenReturnHttpNotFound() throws Exception {
        when(funcionarioController.getTopFuncionarios()).thenThrow(new NoResourceFoundException("/api/v2.0/random"));

        mockMvc.perform(MockMvcRequestBuilders
                    .get("/api/v2.0/random")
                    .accept(MediaType.APPLICATION_JSON)
                    .with(csrf())
                    .with(user("dudu")))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    void handleInsufficientAuthenticationExceptionThenReturnHttpUnauthorized() throws Exception {
        when(funcionarioController.getFuncionario(any(UUID.class))).thenThrow(new FuncionarioNotFoundException("Não foi possível encontrar o funcionário com o id informado"));

        mockMvc.perform(MockMvcRequestBuilders
                    .get("/api/v2.0/funcionarios/{id}", ID)
                    .accept(MediaType.APPLICATION_JSON)
                    .with(csrf()))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.instant").isNotEmpty())
                .andExpect(jsonPath("$.title").value("Token inválido"))
                .andExpect(jsonPath("$.description").value("Token inválido, expirado ou nulo"))
                .andExpect(jsonPath("$.status").isNotEmpty())
                .andExpect(jsonPath("$.path").isNotEmpty());
    }

    @Test
    void handleAccessDeniedExceptionThenReturnHttpForbidden() throws Exception {
        when(funcionarioController.getFuncionarios()).thenThrow(new AccessDeniedException(null));

        mockMvc.perform(MockMvcRequestBuilders
                    .get("/api/v2.0/funcionarios")
                    .accept(MediaType.APPLICATION_JSON)
                    .with(csrf())
                    .with(user("dudu").roles(Setor.GARCOM.getCargo())))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.instant").isNotEmpty())
                .andExpect(jsonPath("$.title").value("Acesso negado"))
                .andExpect(jsonPath("$.description").value("O funcionário não tem permissão para acessar tal recurso"))
                .andExpect(jsonPath("$.status").isNotEmpty())
                .andExpect(jsonPath("$.path").isNotEmpty());
    }

    @Test
    void handleSendEmailExceptionThenReturnHttpInternalServerError() throws Exception {
        when(authenticationController.recoveryPassword(any())).thenThrow(new SendEmailException("Erro ao enviar email"));

        mockMvc.perform(MockMvcRequestBuilders
                    .post("/api/v2.0/auth/recovery")
                    .accept(MediaType.APPLICATION_JSON)
                    .with(csrf())
                    .with(user("dudu"))
                    .content(toJson(new RecoveryPasswordRequest(EMAIL)))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.instant").isNotEmpty())
                .andExpect(jsonPath("$.title").value("Erro no servidor interno"))
                .andExpect(jsonPath("$.description").value("Erro ao enviar email"))
                .andExpect(jsonPath("$.status").isNotEmpty())
                .andExpect(jsonPath("$.path").isNotEmpty());
    }

    @Test
    void handlePasswordNotEqualsExceptionThenReturnHttpUnauthorized() throws Exception {
        when(authenticationController.recoveryPassword(any())).thenThrow(new PasswordNotEqualsException("As senhas não coincidem"));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v2.0/auth/recovery")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .with(user("dudu"))
                        .content(toJson(new ResetPasswordRequest("123", "321")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.instant").isNotEmpty())
                .andExpect(jsonPath("$.title").value("Erro ao alterar a senha"))
                .andExpect(jsonPath("$.description").value("As senhas não coincidem"))
                .andExpect(jsonPath("$.status").isNotEmpty())
                .andExpect(jsonPath("$.path").isNotEmpty());
    }

    private void mockData() {
        funcionarioDTO = new FuncionarioDTO(
                ID,
                NOME,
                EMAIL,
                DATA_NASCIMENTO,
                SETOR
        );
    }

    private String toJson(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}