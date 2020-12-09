package com.management.springgoodsmanagementbackend.controllers;

import com.management.springgoodsmanagementbackend.dtos.ProductPageDTO;
import com.management.springgoodsmanagementbackend.model.Product;
import com.management.springgoodsmanagementbackend.dtos.ProductWithIdDTO;
import com.management.springgoodsmanagementbackend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @RequestMapping(path = "/get/{page}/{size}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ProductPageDTO getProducts(@PathVariable(required = false) Integer page, @PathVariable(required = false) Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        System.out.println(pageRequest);
        return productService.getProducts(pageRequest);
    }

    @RequestMapping(path = "/get/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Optional<Product> getProductsById(@PathVariable Integer id) {
        System.out.println(id);
        return productService.getProductById(id);
    }

    @RequestMapping(path = "/edit", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Product editProduct(@RequestBody ProductWithIdDTO productWithIdDTO) {
        System.out.println(productWithIdDTO);
        return productService.editProduct(productWithIdDTO);
    }

    @RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable Integer id) {
        System.out.println("deleted product !!!!!!!!!!! !!!!!!!!!! !!!!!!!!!!!!!!");
        productService.deleteProduct(id);
    }

}
