package com.deiz0n.makeorderapi.controllers;

import com.deiz0n.makeorderapi.domain.dtos.FuncionarioDTO;
import com.deiz0n.makeorderapi.domain.enums.Setor;
import com.deiz0n.makeorderapi.domain.utils.requests.AuthenticationRequest;
import com.deiz0n.makeorderapi.domain.utils.requests.RecoveryPasswordRequest;
import com.deiz0n.makeorderapi.domain.utils.requests.ResetPasswordRequest;
import com.deiz0n.makeorderapi.domain.utils.responses.ResponseRequest;
import com.deiz0n.makeorderapi.domain.utils.responses.TokenResponse;
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

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {

    private static final String EMAIL = "teste@gmail.com";
    private static final String SENHA = "123";
    private static final Instant INSTANT = Instant.now();
    private static final Integer STATUS = HttpStatus.OK.value();
    private static final UUID FUNCIONARIO_ID = UUID.randomUUID();

    @MockBean
    private AuthenticationController controller;
    @Autowired
    private MockMvc mockMvc;

    private TokenResponse tokenResponse;
    private ResponseRequest responseRecovery;
    private ResponseRequest responseResetPassword;
    private FuncionarioDTO funcionarioDTO;

    @BeforeEach
    void setUp() {
        mockData();
    }

    @Test
    void whenSingInAuthThenReturnHttpOk() throws Exception {
       when(controller.singInAuth(any())).thenReturn(ResponseEntity.ok(tokenResponse));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v2.0/auth/login")
                .accept(MediaType.APPLICATION_JSON)
                    .with(csrf())
                    .with(user("dudu"))
                    .content(toJson(new AuthenticationRequest(EMAIL, SENHA)))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.token").value(tokenResponse.getToken()));

    }

    @Test
    void whenRecoveryPasswordThenReturnHttpOk() throws Exception {
        when(controller.recoveryPassword(any())).thenReturn(ResponseEntity.ok(responseRecovery));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v2.0/auth/recovery")
                .accept(MediaType.APPLICATION_JSON)
                    .with(csrf())
                    .with(user("dudu"))
                    .content(toJson(new RecoveryPasswordRequest(EMAIL)))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.instant").isNotEmpty())
                .andExpect(jsonPath("$.title").value(responseRecovery.getTitle()))
                .andExpect(jsonPath("$.description").value(responseRecovery.getDescription()))
                .andExpect(jsonPath("$.status").value(responseRecovery.getStatus()));
    }

    @Test
    void whenResetPasswordThenReturnHttpOk() throws Exception {
        when(controller.resetPassword(any(UUID.class), any())).thenReturn(ResponseEntity.ok(responseResetPassword));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v2.0/auth/recovery/user")
                    .param("id", FUNCIONARIO_ID.toString())
                    .accept(MediaType.APPLICATION_JSON)
                    .with(csrf())
                    .with(user("dudu"))
                    .content(toJson(new ResetPasswordRequest("123", "123")))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.instant").isNotEmpty())
                .andExpect(jsonPath("$.title").value(responseResetPassword.getTitle()))
                .andExpect(jsonPath("$.description").value(responseResetPassword.getDescription()))
                .andExpect(jsonPath("$.status").value(responseResetPassword.getStatus()));
    }

    @Test
    void whenAuthenticatedThenReturnHttpOk() throws Exception {
        when(controller.authenticated(any())).thenReturn(ResponseEntity.ok(funcionarioDTO));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v2.0/auth/authenticated")
                    .accept(MediaType.APPLICATION_JSON)
                    .with(csrf())
                    .with(user("dudu")))
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.nome").value(funcionarioDTO.getNome()))
                .andExpect(jsonPath("$.email").value(funcionarioDTO.getEmail()))
                .andExpect(jsonPath("$.data_nascimento").isNotEmpty())
                .andExpect(jsonPath("$.setor").value(funcionarioDTO.getSetor().toString()));
    }

    private void mockData() {
        tokenResponse = new TokenResponse("f71b55403c697e9ae37b2332e63913af130b9bd428da291c660362a94af40260");
        responseRecovery = new ResponseRequest(
                INSTANT,
                "Email enviado",
                "Verifique o endereço enviado para o seu endereço de email",
                STATUS
        );
        responseResetPassword = new ResponseRequest(
                INSTANT,
                "Senha alterada",
                "Sua senha foi alterada com sucesso",
                STATUS
        );
        funcionarioDTO = new FuncionarioDTO(
                FUNCIONARIO_ID,
                "Dudu",
                EMAIL,
                Date.from(INSTANT),
                Setor.ADMINISTRACAO
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