package com.management.springgoodsmanagementbackend.repositories;

import com.management.springgoodsmanagementbackend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findByMainCategoryAndSubCategoryAndProductPriceLessThanAndProductPriceGreaterThan(String mainCategory,
                                                                                                    String subCategory,
                                                                                                    Integer priceMin,
                                                                                                    Integer priceMax,
                                                                                                    Pageable pageable);

    Page<Product> findByMainCategoryAndProductPriceLessThanAndProductPriceGreaterThan(String mainCategory,
                                                                                      Integer priceMin,
                                                                                      Integer priceMax,
                                                                                      Pageable pageable);

    Page<Product> findProductsByProductPriceLessThanAndProductPriceGreaterThan(Integer priceMin,
                                                                               Integer priceMax,
                                                                               Pageable pageable);


    Optional<Product> findFirstByMainCategoryAndSubCategoryOrderByProductPriceDesc(String mainCategory,
                                                                                   String subCategory);

    Optional<Product> findFirstByMainCategoryAndSubCategoryOrderByProductPriceAsc(String mainCategory,
                                                                                  String subCategory);

    Optional<Product> findFirstByMainCategoryOrderByProductPriceDesc(String mainCategory);

    Optional<Product> findFirstByMainCategoryOrderByProductPriceAsc(String mainCategory);

    Optional<Product> findFirstByOrderByProductPriceAsc();

    Optional<Product> findFirstByOrderByProductPriceDesc();

    List<Product> findByProductImgUrl(String imgUrl);
}
