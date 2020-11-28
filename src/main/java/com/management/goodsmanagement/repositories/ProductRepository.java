package com.management.goodsmanagement.repositories;

import com.management.goodsmanagement.model.Product;
import com.management.goodsmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByProductName (String productName);
    Product findById(int id);
    Product deleteById(int id);
}
