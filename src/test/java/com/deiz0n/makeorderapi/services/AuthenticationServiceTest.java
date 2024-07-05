package com.deiz0n.makeorderapi.services;

import com.deiz0n.makeorderapi.domain.entities.Funcionario;
import com.deiz0n.makeorderapi.domain.events.GenerateTokenEvent;
import com.deiz0n.makeorderapi.domain.events.GeneratedTokenEvent;
import com.deiz0n.makeorderapi.domain.events.ResetedPasswordEvent;
import com.deiz0n.makeorderapi.domain.utils.requests.AuthenticationRequest;
import com.deiz0n.makeorderapi.domain.utils.requests.ResetPasswordRequest;
import com.deiz0n.makeorderapi.domain.utils.responses.TokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthenticationServiceTest {

    public static final ResetPasswordRequest RESET_PASSWORD = new ResetPasswordRequest("123", "123");
    public static final UUID ID = UUID.randomUUID();

    @InjectMocks
    private AuthenticationService authenticationService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private ApplicationEventPublisher eventPublisher;

    private GeneratedTokenEvent generatedTokenEvent;
    private ResetedPasswordEvent resetedPasswordEvent;
    private GenerateTokenEvent generateTokenEvent;
    private TokenResponse token;
    private AuthenticationRequest authenticationRequest;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockData();
    }

    @Test
    void whenSingInThenReturnTokenResponse() {
        doNothing().when(eventPublisher).publishEvent(generateTokenEvent);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);

        authenticationService.singIn(authenticationRequest);

    }

    @Test
    void whenGetTokenThenSetToken() {
        doNothing().when(eventPublisher).publishEvent(generatedTokenEvent);

        authenticationService.getToken(generatedTokenEvent);

        assertEquals(token, generatedTokenEvent.getResponse());
    }

    @Test
    void whenResetPasswordThenDontReturn() {
        doNothing().when(eventPublisher).publishEvent(resetedPasswordEvent);

        authenticationService.resetPassword(RESET_PASSWORD, ID);

        assertEquals(RESET_PASSWORD.getPassword(), resetedPasswordEvent.getResetPassword().getPassword());
        assertEquals(RESET_PASSWORD.getConfirmPassword(), resetedPasswordEvent.getResetPassword().getConfirmPassword());
    }

    private void mockData() {
        token = new TokenResponse("random token");
        generatedTokenEvent = new GeneratedTokenEvent(this, token);
        resetedPasswordEvent = new ResetedPasswordEvent(ID, RESET_PASSWORD);
        generateTokenEvent = new GenerateTokenEvent(this, (Funcionario) authentication.getPrincipal());
        authenticationRequest = new AuthenticationRequest("test@email.com", "123");
    }
}