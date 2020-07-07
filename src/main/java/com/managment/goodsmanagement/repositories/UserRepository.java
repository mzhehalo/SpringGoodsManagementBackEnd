package com.managment.goodsmanagement.repositories;

import com.managment.goodsmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByNameAndPassword(String name, String password);
}
