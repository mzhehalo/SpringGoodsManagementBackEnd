package com.management.goodsmanagement.services;

import com.management.goodsmanagement.model.User;
import com.management.goodsmanagement.model.UserWithFirstName;
import com.management.goodsmanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public User editUser(UserWithFirstName userWithFirstName) {
        User userEdit = userRepository.findByFirstName(userWithFirstName.getFirstName());
        userEdit.setFirstName(userWithFirstName.getUser().getFirstName());
        userEdit.setLastName(userWithFirstName.getUser().getLastName());
        userEdit.setEmail(userWithFirstName.getUser().getEmail());
        userEdit.setCreated(ZonedDateTime.now());

        userEdit.setPassword(bCryptPasswordEncoder.encode(userWithFirstName.getUser().getPassword()));
        System.out.println(userEdit);
        return userRepository.save(userEdit);
    }
}
