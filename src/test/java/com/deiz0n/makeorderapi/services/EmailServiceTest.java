package com.deiz0n.makeorderapi.services;

import com.deiz0n.makeorderapi.domain.dtos.FuncionarioDTO;
import com.deiz0n.makeorderapi.domain.events.SendEmailEvent;

import com.deiz0n.makeorderapi.domain.exceptions.SendEmailException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
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
    private final String emailFrom = "email@from.com";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockData();
    }

    @Test
    void whenSendThenDontReturn() {
        doNothing().when(eventPublisher).publishEvent(emailEvent);
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        service.send(emailEvent);

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void whenSendThenThrowSendEmailException()  {
        doThrow(SendEmailException.class).when(mailSender).send(any(SimpleMailMessage.class));

        var exception = assertThrows(
                SendEmailException.class,
                () -> service.send(emailEvent)
        );

        assertEquals("Erro ao enviar email", exception.getMessage());
    }

    private void mockData() {
        emailEvent = new SendEmailEvent(this, new FuncionarioDTO());
    }

}