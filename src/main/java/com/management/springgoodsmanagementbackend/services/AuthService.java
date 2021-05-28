package com.management.springgoodsmanagementbackend.services;

import com.management.springgoodsmanagementbackend.dtos.AuthenticationRequest;
import com.management.springgoodsmanagementbackend.dtos.AuthenticationResponse;
import com.management.springgoodsmanagementbackend.model.User;
import com.management.springgoodsmanagementbackend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.*;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect Email or password", e);
        }

        String token = jwtService.generateToken(authenticationRequest.getEmail());
        return new AuthenticationResponse(token);
    }

    public Optional<User> getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principalName = authentication.getName();
        return userRepository.findByEmail(principalName);
    }

    public ResponseEntity<String> registerUser(@Valid User user) {
        Optional<User> userByEmail = userRepository.findByEmail(user.getEmail());
        boolean userAdmin = userRepository.existsUserByEmail("admin@gmail.com");
        if (!userAdmin) {
            User admin = new User();
            admin.setPassword(bCryptPasswordEncoder.encode("admin_admin"));
            admin.setCreated(ZonedDateTime.now());
            admin.setEnabled(false);
            admin.setEmail("admin@gmail.com");
            admin.setFirstName("admin");
            admin.setLastName("admin");
            admin.setRole("ROLE_ADMIN");
            userRepository.save(admin);
        }
        if (userByEmail.isPresent()) {
            log.error("Email already exist");
            return new ResponseEntity<>("Email already exist", HttpStatus.BAD_REQUEST);
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setCreated(ZonedDateTime.now());
            user.setEnabled(false);
            userRepository.save(user);
        }
        return ResponseEntity.ok("User Registered!");
    }
}
