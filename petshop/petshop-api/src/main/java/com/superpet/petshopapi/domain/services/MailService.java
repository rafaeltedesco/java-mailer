package com.superpet.petshopapi.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {
    
    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String to, String suject, String body) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("rafatedesco@teste.com", "rafael tedesco");
        helper.setTo(to);
        helper.setSubject(suject);;
        helper.setText(body, true);
        mailSender.send(message);
    }

}
