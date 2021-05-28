package com.management.springgoodsmanagementbackend.repositories;

import com.management.springgoodsmanagementbackend.model.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartProduct, Integer> {
    Optional<CartProduct> findByProductIdAndCustomerIdAndOrdered(Integer productId, Integer userId, boolean isOrdered);
    List<CartProduct> findAllByProductId(Integer productId);
    List<CartProduct> findAllByCustomerIdAndOrdered(Integer customerId, boolean isOrdered);
}
