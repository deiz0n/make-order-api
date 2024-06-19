package com.deiz0n.makeorderapi.services;

import com.deiz0n.makeorderapi.domain.events.SendEmailEvent;
import com.deiz0n.makeorderapi.domain.exceptions.SendEmailException;
import com.deiz0n.makeorderapi.domain.utils.GenerateCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class EmailService {

    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @EventListener
    private void send(SendEmailEvent event) {
        var message = new SimpleMailMessage();

        message.setSentDate(Date.from(Instant.now()));
        try {

            message.setFrom(emailFrom);
            message.setTo(event.getEmail());
            message.setSubject("Recuperação de senha");
            message.setText("Código aleatório: " + GenerateCode.generate());

            mailSender.send(message);
        } catch (Exception e) {
            throw new SendEmailException("Erro ao enviar email");
        }
    }

}