package com.management.springgoodsmanagementbackend.controllers;

import com.management.springgoodsmanagementbackend.dtos.ProductPageDTO;
import com.management.springgoodsmanagementbackend.model.Product;
import com.management.springgoodsmanagementbackend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4300")
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(value = "add")
    public ResponseEntity addProduct(@RequestParam MultipartFile file,
                                     @RequestParam String mainCategory,
                                     @RequestParam String subCategory,
                                     @RequestParam String productName,
                                     @RequestParam String productDescription,
                                     @RequestParam String productBrand,
                                     @RequestParam String productPrice,
                                     @RequestParam String sellerId
                                     ) {
        Product product = new Product();
        product.setMainCategory(mainCategory);
        product.setSubCategory(subCategory);
        product.setProductName(productName);
        product.setProductDescription(productDescription);
        product.setProductBrand(productBrand);
        product.setProductPrice(Integer.parseInt(productPrice));
        return productService.addProduct(file, product, Integer.parseInt(sellerId));
    }

    @RequestMapping(path = "/get/{page}/{size}/{priceMin}/{priceMax}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ProductPageDTO getProducts(@PathVariable(required = false) Integer page,
                                      @PathVariable(required = false) Integer size,
                                      @PathVariable(required = false) Integer priceMin,
                                      @PathVariable(required = false) Integer priceMax
                                      ) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return productService.getProducts(pageRequest, priceMin, priceMax);
    }

    @RequestMapping(path = "/get/min/max", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Integer> getMinMax(){
        return productService.getMinMax();
    }

    @RequestMapping(path = "/get/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Optional<Product> getProductsById(@PathVariable Integer id) {
        return productService.getProductById(id);
    }

    @RequestMapping(path = "/category/{mainCategory}/{subCategory}/{currentPage}/{size}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ProductPageDTO getProductsByCategory(@PathVariable String mainCategory,
                                               @PathVariable String subCategory,
                                               @PathVariable Integer currentPage,
                                               @PathVariable Integer size) {
        PageRequest pageRequest = PageRequest.of(currentPage - 1, size);
        return productService.getProductByCategory(mainCategory, subCategory, pageRequest);
    }

    @PutMapping(value = "edit")
    public ResponseEntity editProduct(@RequestParam MultipartFile file,
                                      @RequestParam String productName,
                                      @RequestParam String mainCategory,
                                      @RequestParam String subCategory,
                                      @RequestParam String productDescription,
                                      @RequestParam String productBrand,
                                      @RequestParam String productPrice,
                                      @RequestParam String productId) {
        Product product = new Product();
        product.setMainCategory(mainCategory);
        product.setSubCategory(subCategory);
        product.setProductName(productName);
        product.setProductDescription(productDescription);
        product.setProductBrand(productBrand);
        product.setProductPrice(Integer.parseInt(productPrice));
        return productService.editProduct(file ,product, Integer.parseInt(productId));
    }

    @RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
    }

}
