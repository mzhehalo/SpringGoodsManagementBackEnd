package com.management.springgoodsmanagementbackend.controllers;

import com.management.springgoodsmanagementbackend.bean.AuthenticationBean;
import com.management.springgoodsmanagementbackend.services.AuthService;
import com.management.springgoodsmanagementbackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4300")
@Validated
public class AuthController {

    @Autowired
    private AuthService authService;

    @RequestMapping(path = "/user/register", method = RequestMethod.POST)
    public ResponseEntity<String> register(@Valid @RequestBody User user) {
        return authService.registerUser(user);
    }

    @RequestMapping(path = "login", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationBean helloWorldBean() {
        return new AuthenticationBean("You are authenticated");
    }
}
