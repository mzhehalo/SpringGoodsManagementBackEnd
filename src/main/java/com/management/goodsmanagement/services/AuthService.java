package com.management.goodsmanagement.services;

import com.management.goodsmanagement.bean.AuthenticationBean;
import com.management.goodsmanagement.dto.AuthenticationRequest;
import com.management.goodsmanagement.dto.AuthenticationResponse;
import com.management.goodsmanagement.model.User;
import com.management.goodsmanagement.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.*;
import java.util.List;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

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
