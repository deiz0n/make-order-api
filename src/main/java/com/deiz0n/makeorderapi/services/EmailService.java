package com.deiz0n.makeorderapi.services;

import com.deiz0n.makeorderapi.domain.events.SendEmailEvent;
import com.deiz0n.makeorderapi.domain.exceptions.SendEmailException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Date;

@Service
@Getter
public class EmailService {

    @Value("${spring.mail.username}")
    private String emailFrom;

    private JavaMailSender mailSender;

    private final URI uri = new URI("localhost:8080/api/v2.0/auth/recovery/user?id=");

    public EmailService(JavaMailSender mailSender) throws URISyntaxException {
        this.mailSender = mailSender;
    }

    @EventListener
    private void send(SendEmailEvent event) {
        var message = new SimpleMailMessage();

        var url = uri+event.getFuncionario().getId().toString();

        message.setSentDate(Date.from(Instant.now()));
        try {

            message.setFrom(emailFrom);
            message.setTo(event.getFuncionario().getEmail());
            message.setSubject("Recuperação de senha");
            message.setText("Acesse o seguinte endereço para alterar sua senha: " + url);

            mailSender.send(message);
        } catch (Exception e) {
            throw new SendEmailException("Erro ao enviar email");
        }
    }

}
