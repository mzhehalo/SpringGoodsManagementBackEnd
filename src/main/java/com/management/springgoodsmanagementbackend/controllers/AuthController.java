package com.management.springgoodsmanagementbackend.controllers;

import com.management.springgoodsmanagementbackend.dtos.AuthenticationRequest;
import com.management.springgoodsmanagementbackend.dtos.AuthenticationResponse;
import com.management.springgoodsmanagementbackend.services.AuthService;
import com.management.springgoodsmanagementbackend.model.User;
import com.management.springgoodsmanagementbackend.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4300")
@Validated
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping(path = "/user/register", method = RequestMethod.POST)
    public ResponseEntity<String> register(@Valid @RequestBody User user) {
        return authService.registerUser(user);
    }

    @RequestMapping(path = "login", method = RequestMethod.POST)
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect Email or password", e);
        }

        String token = jwtService.generateToken(authenticationRequest.getEmail());
        return new AuthenticationResponse(token);
    }
}
