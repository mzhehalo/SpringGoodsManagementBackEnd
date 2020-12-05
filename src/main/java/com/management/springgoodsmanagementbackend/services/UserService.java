package com.management.springgoodsmanagementbackend.services;

import com.management.springgoodsmanagementbackend.model.User;
import com.management.springgoodsmanagementbackend.dtos.UserWithFirstNameDTO;
import com.management.springgoodsmanagementbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    public User getUser(String firstName) {
        return userRepository.findByFirstName(firstName);
    }

    public User editUser(UserWithFirstNameDTO userWithFirstNameDTO) {
        User userEdit = userRepository.findByFirstName(userWithFirstNameDTO.getFirstName());
        userEdit.setFirstName(userWithFirstNameDTO.getUser().getFirstName());
        userEdit.setLastName(userWithFirstNameDTO.getUser().getLastName());
        userEdit.setEmail(userWithFirstNameDTO.getUser().getEmail());
        userEdit.setCreated(ZonedDateTime.now());

        userEdit.setPassword(bCryptPasswordEncoder.encode(userWithFirstNameDTO.getUser().getPassword()));
        System.out.println(userEdit);
        return userRepository.save(userEdit);
    }
}
