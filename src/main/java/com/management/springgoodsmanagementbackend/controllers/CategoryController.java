package com.management.springgoodsmanagementbackend.controllers;

import com.management.springgoodsmanagementbackend.model.Category;
import com.management.springgoodsmanagementbackend.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4300")
@RequestMapping(path = "/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(path = "/get", method = RequestMethod.GET)
    public List<Category> getCategories(){
        return categoryService.getCategories();
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public List<Category> addCategory(@RequestBody Category category){
        System.out.println(category.getMainCategory());
        System.out.println(category.getSubCategory());
        return categoryService.addCategories(category);
    }

    @RequestMapping(path = "/update", method = RequestMethod.PUT)
    public ResponseEntity<String> updateCategory(@RequestBody Category category){
       return categoryService.updateCategory(category);
    }

    @RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteCategory(@PathVariable Integer id) {
       return categoryService.deleteCategory(id);
    }
}
