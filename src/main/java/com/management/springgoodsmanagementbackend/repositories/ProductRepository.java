package com.management.springgoodsmanagementbackend.repositories;

import com.management.springgoodsmanagementbackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByProductName (String productName);
    Product findById(int id);
    Product deleteById(int id);
}
