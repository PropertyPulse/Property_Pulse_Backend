package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public interface EmailSenderService {
    void sendSimpleEmail(String toEmail,String subject,String body);
    void sendUserPasswordViaEmail(String toEmail, String password);

}
