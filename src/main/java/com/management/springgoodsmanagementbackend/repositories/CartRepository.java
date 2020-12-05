package com.management.springgoodsmanagementbackend.repositories;

import com.management.springgoodsmanagementbackend.model.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartProduct, Integer> {
    Optional<CartProduct> findByProductIdAndUserId(Integer productId, Integer userId);
}
