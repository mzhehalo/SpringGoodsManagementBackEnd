package com.management.springgoodsmanagementbackend.services;

import com.management.springgoodsmanagementbackend.model.Category;
import com.management.springgoodsmanagementbackend.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public List<Category> addCategories(Category categories) {
        categoryRepository.save(categories);
        return categoryRepository.findAll();
    }

    public ResponseEntity<String> updateCategory(Category category) {
        Optional<Category> byId = categoryRepository.findById(category.getId());
        byId.ifPresent(category1 -> {
            category1.setMainCategory(category.getMainCategory());
            category1.setSubCategory(category.getSubCategory());
            categoryRepository.save(category1);
        });
        return ResponseEntity.ok("Updated!");
    }

    public ResponseEntity<String> deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
        return ResponseEntity.ok("Deleted!");
    }
}
