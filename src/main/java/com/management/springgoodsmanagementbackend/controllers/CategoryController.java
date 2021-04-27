package com.management.springgoodsmanagementbackend.controllers;

import com.management.springgoodsmanagementbackend.model.MainCategory;
import com.management.springgoodsmanagementbackend.model.SubCategory;
import com.management.springgoodsmanagementbackend.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<MainCategory> getCategories(){
        return categoryService.getCategories();
    }

    @RequestMapping(path = "/add/main", method = RequestMethod.POST)
    public ResponseEntity<String> addCategory(@RequestBody MainCategory mainCategory){
        return categoryService.addMainCategory(mainCategory);
    }

    @RequestMapping(path = "/add/sub/{idMain}", method = RequestMethod.POST)
    public List<MainCategory> addCategory(@PathVariable Integer idMain,
                                          @RequestBody SubCategory subCategory){
        return categoryService.addSubCategory(idMain, subCategory);
    }

    @RequestMapping(path = "/update/main", method = RequestMethod.PUT)
    public ResponseEntity<String> updateCategory(@RequestBody MainCategory mainCategory){
       return categoryService.updateMainCategory(mainCategory);
    }

    @RequestMapping(path = "/update/sub", method = RequestMethod.PUT)
    public ResponseEntity<String> updateCategory(@RequestBody SubCategory subCategory){
       return categoryService.updateSubCategory(subCategory);
    }

    @RequestMapping(path = "/delete/main/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteMainCategory(@PathVariable Integer id) {
       return categoryService.deleteMainCategory(id);
    }

    @RequestMapping(path = "/delete/sub/{subId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteSubCategory(@PathVariable Integer subId) {
       return categoryService.deleteSubCategory(subId);
    }
}
