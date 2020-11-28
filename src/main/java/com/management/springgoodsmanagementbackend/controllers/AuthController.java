package com.management.springgoodsmanagementbackend.controllers;

import com.management.springgoodsmanagementbackend.bean.AuthenticationBean;
import com.management.springgoodsmanagementbackend.services.AuthService;
import com.management.springgoodsmanagementbackend.services.UserService;
import com.management.springgoodsmanagementbackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4300")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public List<User> register(@RequestBody User user) {
        System.out.println(user);
        return authService.registerUser(user);
    }

//    @RequestMapping(path = "/login", method = RequestMethod.GET)
//    @ResponseStatus(HttpStatus.OK)
//    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest) {
//        System.out.println(authenticationRequest);
//        return authService.login(authenticationRequest);
//    }

    @RequestMapping(path = "login", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationBean helloWorldBean() {
        return new AuthenticationBean("You are authenticated");
    }
}
