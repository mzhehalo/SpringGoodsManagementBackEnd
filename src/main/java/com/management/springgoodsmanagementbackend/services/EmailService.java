package com.management.springgoodsmanagementbackend.services;

import com.management.springgoodsmanagementbackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.List;

@EnableAsync
@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public void sendEmail(List<String> listSellerEmailWithoutDuplicates, User user) {
        listSellerEmailWithoutDuplicates.forEach(email -> {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("goodsmanagement1@gmail.com");
            simpleMailMessage.setTo(email);
            simpleMailMessage.setSubject("You have new Order! Login your cabinet!");
            simpleMailMessage.setText("You have new Order: Customer First Name: " + user.getFirstName() +
                    " and Last Name: " + user.getLastName());
            javaMailSender.send(simpleMailMessage);
        });
    }
}
