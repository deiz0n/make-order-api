package com.deiz0n.makeorderapi.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.deiz0n.makeorderapi.domain.events.GenerateTokenEvent;
import com.deiz0n.makeorderapi.domain.events.GeneratedTokenEvent;
import com.deiz0n.makeorderapi.domain.utils.responses.TokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    public static final String ISSUER = "mk-api";

    private ApplicationEventPublisher eventPublisher;


    @Value("${api.secret.token.key}")
    private String secret;

    public TokenService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void generateToken(GenerateTokenEvent event) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            var token = new TokenResponse(
                    JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(event.getFuncionario().getEmail())
                    .withExpiresAt(expirationInstant())
                    .sign(algorithm));

            var generatedToken = new GeneratedTokenEvent(this, token);
            eventPublisher.publishEvent(generatedToken);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao criar token", e);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return "";
        }
    }

    public Instant expirationInstant() {
        return LocalDateTime.now().plusHours(8).toInstant(ZoneOffset.of("-03:00"));
    }

}
