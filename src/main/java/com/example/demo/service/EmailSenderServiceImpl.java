package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {
    @Value("${spring.mail.username}")
    private String emailUsername;


    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendSimpleEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailUsername);
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);

    }

    @Override
    public void sendUserPasswordViaEmail(String toEmail, String password) {
        // Create a SimpleMailMessage
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailUsername);
        message.setTo(toEmail);

        // Set the subject and email body
        String subject = "Your New Account Password";
        String emailBody = "Dear User,\n\n"
                + "Your new account password is: " + password + "\n\n"
                + "Please keep it secure and do not share it with anyone.\n\n"
                + "Best regards,\n"
                + "Property Pulse";

        message.setSubject(subject);
        message.setText(emailBody);

        // Send the email
        mailSender.send(message);
    }
}
