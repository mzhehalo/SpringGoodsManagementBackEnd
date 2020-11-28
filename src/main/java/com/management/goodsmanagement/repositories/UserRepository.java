package com.management.goodsmanagement.repositories;

import com.management.goodsmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByFirstName(String firstName);
}
