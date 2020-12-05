package com.management.springgoodsmanagementbackend.services;

import com.management.springgoodsmanagementbackend.model.Product;
import com.management.springgoodsmanagementbackend.dtos.ProductWithIdDTO;
import com.management.springgoodsmanagementbackend.model.User;
import com.management.springgoodsmanagementbackend.repositories.ProductRepository;
import com.management.springgoodsmanagementbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public Product addProduct(ProductWithIdDTO productWithIdDTO) {
        System.out.println("Service 1:" + productWithIdDTO);
        productWithIdDTO.getProduct().setProductCreated(ZonedDateTime.now());
        userRepository.findById(productWithIdDTO.getId()).ifPresent(user -> {
            productWithIdDTO.getProduct().setProductSeller(user);
        });
        System.out.println("Service 2:" + productWithIdDTO);
        return productRepository.save(productWithIdDTO.getProduct());
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Integer id) {
        return productRepository.findById(id);
    }

    public Product editProduct(ProductWithIdDTO productWithIdDTO) {
        Product productEdit = productRepository.findById(productWithIdDTO.getId());

        productEdit.setProductName(productWithIdDTO.getProduct().getProductName());
        productEdit.setProductDescription(productWithIdDTO.getProduct().getProductDescription());
        productEdit.setProductCreated(ZonedDateTime.now());
        productEdit.setProductBrand(productWithIdDTO.getProduct().getProductBrand());
        productEdit.setProductPrice(productWithIdDTO.getProduct().getProductPrice());
            return productRepository.save(productEdit);

    }

    public void deleteProduct(Integer id) {
        Optional<Product> productbyId = productRepository.findById(id);
        List<User> users = userRepository.findAll();
        for (User user: users){
            productbyId.ifPresent(product ->
            user.getProductsWishList().remove(product));
        }
        productRepository.deleteById(id);
    }
}
