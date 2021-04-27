package com.management.springgoodsmanagementbackend.repositories;

import com.management.springgoodsmanagementbackend.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer> {
}
