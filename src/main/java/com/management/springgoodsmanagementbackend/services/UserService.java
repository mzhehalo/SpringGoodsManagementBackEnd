package com.management.springgoodsmanagementbackend.services;

import com.management.springgoodsmanagementbackend.model.Product;
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

import javax.persistence.EntityNotFoundException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ProductService productService;

    @Autowired
    private AuthService authService;

    public User getAuthUser() {
        return authService.getAuthUser().get();
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("No user found!"));
    }

    public ResponseEntity<String> editUser(User user) {
        Optional<User> userById = userRepository.findById(user.getId());
        Optional<User> userRepositoryByEmail = userRepository.findByEmail(user.getEmail());
        boolean isEmailMatch = userById.filter(user1 -> user1.getEmail().equals(user.getEmail())).isPresent();

        if (userRepositoryByEmail.isPresent()) {
            if (isEmailMatch && userRepositoryByEmail.get().getId() == user.getId()) {
                userById.ifPresent(userByIdFount -> {
                    userByIdFount.setFirstName(user.getFirstName());
                    userByIdFount.setLastName(user.getLastName());
                    userByIdFount.setCreated(ZonedDateTime.now());
                    userByIdFount.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                    userRepository.save(userByIdFount);
                });
            }

            if (userRepositoryByEmail.get().getId() != user.getId()) {
                log.error("User with this email already exist");
                return new ResponseEntity<>("User with this email already exist", HttpStatus.BAD_REQUEST);
            }

        } else {
            userById.ifPresent(userByIdFount -> {
                userByIdFount.setFirstName(user.getFirstName());
                userByIdFount.setLastName(user.getLastName());
                userByIdFount.setEmail(user.getEmail());
                userByIdFount.setCreated(ZonedDateTime.now());
                userByIdFount.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                userRepository.save(userByIdFount);
            });
        }

        return ResponseEntity.ok("User edited!");
    }

    public ResponseEntity<String> deleteUser() {

        Optional<User> authUser = authService.getAuthUser();

        authUser.ifPresent(user -> {
            List<Product> productList = user.getProductList();
            productList.forEach(product -> productService.deleteProduct(product.getId()));
        });

        userRepository.deleteById(authUser.get().getId());
        return ResponseEntity.ok("User Deleted!");
    }

    public ResponseEntity<String> deleteUserById(Integer userId) {
        Optional<User> byId = userRepository.findById(userId);

        byId.ifPresent(user -> {
            List<Product> productList = user.getProductList();
            productList.forEach(product -> productService.deleteProduct(product.getId()));
        });

        userRepository.deleteById(userId);
        return ResponseEntity.ok("User Deleted!");
    }
}
