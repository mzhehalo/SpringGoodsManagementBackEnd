package com.management.springgoodsmanagementbackend.controllers;

import com.management.springgoodsmanagementbackend.model.User;
import com.management.springgoodsmanagementbackend.dtos.UserWithFirstNameDTO;
import com.management.springgoodsmanagementbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4300")
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/get", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@RequestBody String firstName) {
        System.out.println(firstName);
        return userService.getUser(firstName);
    }

    @RequestMapping(path = "/edit", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public User editUser(@RequestBody UserWithFirstNameDTO userWithFirstNameDTO){
        System.out.println(userWithFirstNameDTO);
        return userService.editUser(userWithFirstNameDTO);
    }
}
