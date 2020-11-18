package com.management.goodsmanagement.services;

import com.management.goodsmanagement.model.User;
import com.management.goodsmanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String firstName) throws UsernameNotFoundException {
        User byFirstName = userRepository.findByFirstName(firstName);
        if (byFirstName == null) {
            throw new UsernameNotFoundException(firstName);
        }
        return new org.springframework.security.core.userdetails.User(byFirstName.getFirstName(),
                byFirstName.getPassword(),byFirstName.getAuthorities());
    }
}
