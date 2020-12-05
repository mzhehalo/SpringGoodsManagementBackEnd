package com.management.springgoodsmanagementbackend.controllers;

import com.management.springgoodsmanagementbackend.model.Product;
import com.management.springgoodsmanagementbackend.dtos.ProductWithIdDTO;
import com.management.springgoodsmanagementbackend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4300")
@RequestMapping(path = "/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Product addProduct(@RequestBody ProductWithIdDTO productWithIdDTO) {
        System.out.println("Controller:" + productWithIdDTO);
        System.out.println(productWithIdDTO.getId());
        return productService.addProduct(productWithIdDTO);
    }

    @RequestMapping(path = "/get", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Product> getProducts(){
        return productService.getProducts();
    }

    @RequestMapping(path = "/get/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Optional<Product> getProductsById(@PathVariable  Integer id){
        System.out.println(id);
        return productService.getProductById(id);
    }

    @RequestMapping(path = "/edit", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Product editProduct(@RequestBody ProductWithIdDTO productWithIdDTO){
        System.out.println(productWithIdDTO);
        return productService.editProduct(productWithIdDTO);
    }

    @RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable Integer id){
        productService.deleteProduct(id);
    }

}
