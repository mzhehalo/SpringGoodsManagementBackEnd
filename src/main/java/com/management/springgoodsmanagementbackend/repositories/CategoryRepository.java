package com.management.springgoodsmanagementbackend.repositories;

import com.management.springgoodsmanagementbackend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
