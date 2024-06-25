package com.deiz0n.makeorderapi.services;

import com.deiz0n.makeorderapi.domain.entities.Funcionario;
import com.deiz0n.makeorderapi.domain.events.GenerateTokenEvent;
import com.deiz0n.makeorderapi.domain.events.GeneratedTokenEvent;
import com.deiz0n.makeorderapi.domain.events.ResetedPasswordEvent;
import com.deiz0n.makeorderapi.domain.utils.requests.AuthenticationRequest;
import com.deiz0n.makeorderapi.domain.utils.requests.ResetPasswordRequest;
import com.deiz0n.makeorderapi.domain.utils.responses.TokenResponse;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthenticationService {

    private AuthenticationManager authenticationManager;
    private ApplicationEventPublisher eventPublisher;
    private TokenResponse token;

    public AuthenticationService(AuthenticationManager authenticationManager, ApplicationEventPublisher eventPublisher) {
        this.authenticationManager = authenticationManager;
        this.eventPublisher = eventPublisher;
    }

    public TokenResponse singIn(AuthenticationRequest newData) {
        var user = new UsernamePasswordAuthenticationToken(newData.getEmail(), newData.getSenha());
        var authentication = authenticationManager.authenticate(user);

        var generateToken = new GenerateTokenEvent(this, (Funcionario) authentication.getPrincipal());
        eventPublisher.publishEvent(generateToken);

        return token;
    }

    @EventListener
    public void getToken(GeneratedTokenEvent event) {
        token = event.getResponse();
    }

    public void resetPassword(ResetPasswordRequest newData, UUID funcionarioId) {
        var resetedPasswordEvent = new ResetedPasswordEvent(funcionarioId, newData);
        eventPublisher.publishEvent(resetedPasswordEvent);
    }
}
