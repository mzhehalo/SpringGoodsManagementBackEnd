package com.managment.goodsmanagement.controllers;

import com.managment.goodsmanagement.model.User;
import com.managment.goodsmanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4300")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public List<User> register(@RequestBody User user) {
        userRepository.save(user);
        System.out.println(user);

        return userRepository.findAll();
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public User login(@RequestBody User userValidation) {

        return userRepository.findUserByNameAndPassword(userValidation.getName(),
                userValidation.getPassword());
    }
}
