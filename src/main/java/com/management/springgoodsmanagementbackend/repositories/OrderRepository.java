package com.management.springgoodsmanagementbackend.repositories;

import com.management.springgoodsmanagementbackend.model.Ordering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Ordering, Integer> {
}
