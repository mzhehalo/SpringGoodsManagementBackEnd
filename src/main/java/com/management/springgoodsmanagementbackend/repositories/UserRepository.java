package com.management.springgoodsmanagementbackend.repositories;

import com.management.springgoodsmanagementbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByFirstName(String firstName);
    User findByEmail(String email);
    boolean existsUserByEmail(String email);
}
