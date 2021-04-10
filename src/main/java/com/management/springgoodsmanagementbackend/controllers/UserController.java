package com.management.springgoodsmanagementbackend.controllers;

import com.management.springgoodsmanagementbackend.dtos.UserWithEmailDTO;
import com.management.springgoodsmanagementbackend.model.User;
import com.management.springgoodsmanagementbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4300")
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/get", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@RequestBody String email) {
        return userService.getUser(email);
    }

    @RequestMapping(path = "/edit", method = RequestMethod.PUT)
    public ResponseEntity<String> editUser(@RequestBody @Valid UserWithEmailDTO userWithEmailDTO){
        return userService.editUser(userWithEmailDTO);
    }
}
