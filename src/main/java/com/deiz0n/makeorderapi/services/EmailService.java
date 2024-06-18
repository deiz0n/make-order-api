package com.deiz0n.makeorderapi.services;

import com.deiz0n.makeorderapi.domain.events.SendEmailEvent;
import com.deiz0n.makeorderapi.domain.exceptions.SendEmailException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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
        try {
            var message = new SimpleMailMessage();

            message.setFrom(emailFrom);
            message.setTo(event.getMensagem().getDestinatario());
            message.setSubject(event.getMensagem().getAssunto());
            message.setText(event.getMensagem().getCorpo());

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("erro ao enviar email", e);
        }
    }

}
