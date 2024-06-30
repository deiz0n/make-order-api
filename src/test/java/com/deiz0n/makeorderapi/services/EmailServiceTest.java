package com.deiz0n.makeorderapi.services;

import com.deiz0n.makeorderapi.domain.dtos.FuncionarioDTO;
import com.deiz0n.makeorderapi.domain.events.SendEmailEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

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
        doNothing().when(eventPublisher).publishEvent(emailEvent);
        doNothing().when(mailSender).send(new SimpleMailMessage());

        service.send(emailEvent);
    }

    private void mockData() {
        emailEvent = new SendEmailEvent(this, new FuncionarioDTO());
    }

}