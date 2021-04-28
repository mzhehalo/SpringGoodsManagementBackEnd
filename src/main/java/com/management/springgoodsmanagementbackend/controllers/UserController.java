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

    @RequestMapping(path = "/get/email", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public User getUserByEmail(@RequestBody String email) {
        return userService.getUserByEmail(email);
    }

    @RequestMapping(path = "/get/{userId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable Integer userId) {
        return userService.getUser(userId);
    }

    @RequestMapping(path = "/get/all", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @RequestMapping(path = "/edit/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<String> editUser(@RequestBody @Valid User user,
                                           @PathVariable Integer userId
                                           ) {
        return userService.editUser(userId, user);
    }

    @RequestMapping(path = "/delete/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted!");
    }
}
