package com.management.goodsmanagement.services;

import com.management.goodsmanagement.model.User;
import com.management.goodsmanagement.model.UserWithString;
import com.management.goodsmanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(String firstName) {
        return userRepository.findByFirstName(firstName);
    }

    public User editUser(UserWithString userWithString) {
        User userEdit = userRepository.findByFirstName(userWithString.getFirstName());
        userEdit.setFirstName(userWithString.getUser().getFirstName());
        userEdit.setLastName(userWithString.getUser().getLastName());
        userEdit.setEmail(userWithString.getUser().getEmail());
        userEdit.setCreated(ZonedDateTime.now());
        userEdit.setPassword(bCryptPasswordEncoder.encode(userWithString.getUser().getPassword()));
        System.out.println(userEdit);
        return userRepository.save(userEdit);
    }
}
