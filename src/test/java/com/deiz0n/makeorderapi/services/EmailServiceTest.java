package com.deiz0n.makeorderapi.services;

import com.deiz0n.makeorderapi.domain.dtos.FuncionarioDTO;
import com.deiz0n.makeorderapi.domain.events.SendEmailEvent;
import com.deiz0n.makeorderapi.domain.exceptions.SendEmailException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

@SpringBootTest
class EmailServiceTest {

    @InjectMocks
    private EmailService service;
    @Mock
    private ApplicationEventPublisher eventPublisher;
    @Mock
    private JavaMailSender mailSender;

    private SendEmailEvent emailEvent;
    @Value("${spring.mail.username}")
    private String emailFrom;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockData();
    }

    @Test
    void whenSendThenDontReturn() {
        doNothing().when(mailSender).send(new SimpleMailMessage());

        doNothing().when(eventPublisher).publishEvent(emailEvent);

        service.send(emailEvent);
    }

    private void mockData() {
        emailEvent = new SendEmailEvent(UUID.randomUUID(), new FuncionarioDTO());
    }
}