package com.management.springgoodsmanagementbackend.services;

import com.management.springgoodsmanagementbackend.model.User;
import com.management.springgoodsmanagementbackend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.*;

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
    AuthenticationManager authenticationManager;

    public ResponseEntity<String> registerUser(@Valid User user) {
        User userByEmail = userRepository.findByEmail(user.getEmail());
        boolean userAdmin = userRepository.existsUserByEmail("admin@gmail.com");
        if (userAdmin) {
            log.error("Admin already exist");
        } else {
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
        if (userByEmail != null) {
            log.error("Email already exist");
            return new ResponseEntity<String>("Email already exist", HttpStatus.BAD_REQUEST);
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setCreated(ZonedDateTime.now());
            user.setEnabled(false);
            userRepository.save(user);
        }
        return ResponseEntity.ok("Registered");
    }
}
