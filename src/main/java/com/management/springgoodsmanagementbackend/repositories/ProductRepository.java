package com.management.springgoodsmanagementbackend.repositories;

import com.management.springgoodsmanagementbackend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findByMainCategoryAndSubCategory(String mainCategory, String subCategory, Pageable pageable);

    Page<Product> findByMainCategory(String mainCategory, Pageable pageable);

    Page<Product> findProductsByProductPriceLessThanAndProductPriceGreaterThan(Integer priceMin, Integer priceMax,
                                                                       Pageable pageable);
    Optional<Product> findFirstByOrderByProductPriceAsc();
    Optional<Product> findFirstByOrderByProductPriceDesc();

    Product findById(int id);
}
