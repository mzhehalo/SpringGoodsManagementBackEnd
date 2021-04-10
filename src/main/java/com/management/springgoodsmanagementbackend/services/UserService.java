package com.management.springgoodsmanagementbackend.services;

import com.management.springgoodsmanagementbackend.dtos.UserWithEmailDTO;
import com.management.springgoodsmanagementbackend.model.User;
import com.management.springgoodsmanagementbackend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    public User getUser(String email) {
        return userRepository.findByEmail(email);
    }

    public ResponseEntity<String> editUser(UserWithEmailDTO userWithEmailDTO) {
        boolean userByEmail = userRepository.existsUserByEmail(userWithEmailDTO.getUser().getEmail());
        User userByEmailFind = userRepository.findByEmail(userWithEmailDTO.getEmail());
        if (userByEmail) {
            log.error("Email already exist");
            return new ResponseEntity<String>("Email already exist", HttpStatus.BAD_REQUEST);
        } else {
            userByEmailFind.setFirstName(userWithEmailDTO.getUser().getFirstName());
            userByEmailFind.setLastName(userWithEmailDTO.getUser().getLastName());
            userByEmailFind.setEmail(userWithEmailDTO.getUser().getEmail());
            userByEmailFind.setCreated(ZonedDateTime.now());
            userByEmailFind.setPassword(bCryptPasswordEncoder.encode(userWithEmailDTO.getUser().getPassword()));
            userRepository.save(userByEmailFind);
        }
        return ResponseEntity.ok("User edited");
    }
}
