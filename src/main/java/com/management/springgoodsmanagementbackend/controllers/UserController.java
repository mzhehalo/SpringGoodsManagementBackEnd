package com.management.springgoodsmanagementbackend.controllers;

import com.management.springgoodsmanagementbackend.model.User;
import com.management.springgoodsmanagementbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4300")
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/get", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public User getAuthUser() {
        return userService.getAuthUser();
    }

    @RequestMapping(path = "/get/all", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @RequestMapping(path = "/admin/get/{userId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public User getUserById(@PathVariable Integer userId) {
        return userService.getUserById(userId);
    }

    @RequestMapping(path = "/edit", method = RequestMethod.PUT)
    public ResponseEntity<String> editUser(@RequestBody @Valid User user) {
        return userService.editUser(user);
    }

    @RequestMapping(path = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser() {
        return userService.deleteUser();
    }

    @RequestMapping(path = "/admin/delete/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUserById(@PathVariable Integer userId) {
        return userService.deleteUserById(userId);
    }
}
