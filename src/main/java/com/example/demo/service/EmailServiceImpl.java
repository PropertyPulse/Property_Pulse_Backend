//package com.example.demo.service;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmailServiceImpl implements EmailService {
//    @Autowired
//    private JavaMailSender javaMailSender;
//
//    @Override
//    public void sendSimpleMessage(SimpleMailMessage message) {
//        javaMailSender.send(message);
//    }
//}