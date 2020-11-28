package com.management.springgoodsmanagementbackend.services;

import com.management.springgoodsmanagementbackend.model.User;
import com.management.springgoodsmanagementbackend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class AuthService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

//    @Autowired
//    private AuthenticationResponse authenticationResponse;

    @Autowired
    AuthenticationManager authenticationManager;

    public List<User> registerUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setCreated(ZonedDateTime.now());
        user.setEnabled(false);
        userRepository.save(user);
        System.out.println(user);

        return userRepository.findAll();
    }

//    public AuthenticationResponse login( AuthenticationRequest requestBody) {
//        UsernamePasswordAuthenticationToken authenticationTokenRequest = new
//                UsernamePasswordAuthenticationToken(requestBody.getFirstName(), requestBody.getPassword());
//        System.out.println(requestBody.getFirstName() + requestBody.getPassword());
//        try {
//            Authentication authentication = this.authenticationManager.authenticate(authenticationTokenRequest);
//            SecurityContext securityContext = SecurityContextHolder.getContext();
//            securityContext.setAuthentication(authentication);
//
//            /*HttpSession session = httpServletRequest.getSession(true);
//            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);*/
//
////            User user = (User) authentication.getPrincipal();
////            log.info("Logged in user: {}", user);
////            HttpSession session = httpServletRequest.getSession(true);
////            session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, session);
////            System.out.println(authentication.isAuthenticated());
//            if (authenticationTokenRequest.isAuthenticated()) {
//                SecurityContextHolder.getContext().setAuthentication(authenticationTokenRequest);
//                log.debug(String.format("Auto login %s successfully!", requestBody.getFirstName()));
//            }
//
//
//        } catch (BadCredentialsException ex) {
//            return new AuthenticationResponse(false);
//        }
//        return new AuthenticationResponse(true);
//
//    }
//
//    @GetMapping(path = "/login")
//    public AuthenticationBean helloWorldBean() {
//        return new AuthenticationBean("You are authenticated");
//    }

}
