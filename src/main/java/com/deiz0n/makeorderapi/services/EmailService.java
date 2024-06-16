package com.deiz0n.makeorderapi.services;

import com.deiz0n.makeorderapi.domain.exceptions.SendEmailException;
import com.deiz0n.makeorderapi.domain.utils.Mensagem;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

public class EmailService {

    private JavaMailSender mailSender;

    @Value("${mk-api.email.remetente}")
    private String emailFrom;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    private void send(Mensagem msg) {
        try {
            MimeMessage mimeMailMessage = mailSender.createMimeMessage();

            var helper = new MimeMessageHelper(mimeMailMessage, "UTF-8");
            helper.setFrom(emailFrom);
            helper.setTo(msg.getDestinatario().toArray(new String[0]));
            helper.setSubject(msg.getAssunto());
            helper.setText(msg.getCorpo(), true);

        } catch (Exception e) {
            throw new SendEmailException("Erro ao enviar o email");
        }
    }

}
