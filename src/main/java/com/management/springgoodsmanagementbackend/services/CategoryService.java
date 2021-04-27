package com.management.springgoodsmanagementbackend.services;

import com.management.springgoodsmanagementbackend.model.MainCategory;
import com.management.springgoodsmanagementbackend.model.SubCategory;
import com.management.springgoodsmanagementbackend.repositories.MainCategoryRepository;
import com.management.springgoodsmanagementbackend.repositories.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private MainCategoryRepository mainCategoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    public List<MainCategory> getCategories() {
        return mainCategoryRepository.findAll();
    }

    public ResponseEntity<String> addMainCategory(MainCategory categories) {
        mainCategoryRepository.save(categories);
        return ResponseEntity.ok("Added Main!");
    }

    public List<MainCategory> addSubCategory(Integer idMain, SubCategory subCategory) {
        Optional<MainCategory> byId = mainCategoryRepository.findById(idMain);
        byId.ifPresent(mainCategory -> {
            mainCategory.getSubCategories().add(subCategory);
            mainCategoryRepository.save(mainCategory);
        });
        return mainCategoryRepository.findAll();
    }

    public ResponseEntity<String> updateMainCategory(MainCategory mainCategory) {
        Optional<MainCategory> byId = mainCategoryRepository.findById(mainCategory.getId());
        byId.ifPresent(mainCategory1 -> {
            mainCategory1.setMainCategory(mainCategory.getMainCategory());
            mainCategoryRepository.save(mainCategory1);
        });
        return ResponseEntity.ok("Updated Main!");
    }

    public ResponseEntity<String> updateSubCategory(SubCategory subCategory) {
        Optional<SubCategory> byId = subCategoryRepository.findById(subCategory.getId());

        byId.ifPresent(subCategory1 -> {
            subCategory1.setSubCategory(subCategory.getSubCategory());
            subCategoryRepository.save(subCategory1);
        });
        return ResponseEntity.ok("Updated Sub!");
    }

    public ResponseEntity<String> deleteMainCategory(Integer id) {
        mainCategoryRepository.deleteById(id);
        return ResponseEntity.ok("Deleted Main!");
    }

    public ResponseEntity<String> deleteSubCategory(Integer subId) {

        List<MainCategory> allMainCategories = mainCategoryRepository.findAll();

        for (MainCategory mainCategory : allMainCategories) {

            mainCategory.getSubCategories().removeIf(subCategory -> subCategory.getId() == subId);
        }

        subCategoryRepository.deleteById(subId);
        return ResponseEntity.ok("Deleted Sub!");
    }
}
