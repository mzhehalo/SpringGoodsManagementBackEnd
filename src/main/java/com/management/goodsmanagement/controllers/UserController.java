package com.management.goodsmanagement.controllers;

import com.management.goodsmanagement.model.User;
import com.management.goodsmanagement.model.UserWithFirstName;
import com.management.goodsmanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4300")
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(path = {"/users","/users2"},  method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUsers() {

        return userService.getUsers();
    }

    @RequestMapping(path = "/get", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@RequestBody String firstName) {
        System.out.println(firstName);
        return userService.getUser(firstName);
    }

    @RequestMapping(path = "/edit", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public User editUser(@RequestBody UserWithFirstName userWithFirstName){
        System.out.println(userWithFirstName);
        return userService.editUser(userWithFirstName);
    }
}
