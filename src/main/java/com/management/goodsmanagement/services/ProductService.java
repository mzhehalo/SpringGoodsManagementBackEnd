package com.management.goodsmanagement.services;

import com.management.goodsmanagement.model.Product;
import com.management.goodsmanagement.model.ProductWithID;
import com.management.goodsmanagement.model.User;
import com.management.goodsmanagement.repositories.ProductRepository;
import com.management.goodsmanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public Product addProduct(ProductWithID productWithID) {
        System.out.println("Service 1:" + productWithID);
        productWithID.getProduct().setProductCreated(ZonedDateTime.now());
        userRepository.findById(productWithID.getId()).ifPresent(user -> {
            productWithID.getProduct().setProductSeller(user);
        });
        System.out.println("Service 2:" + productWithID);
        return productRepository.save(productWithID.getProduct());
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Integer id) {
        return productRepository.findById(id);
    }

    public Product editProduct(ProductWithID productWithID) {
        Product productEdit = productRepository.findById(productWithID.getId());

        productEdit.setProductName(productWithID.getProduct().getProductName());
        productEdit.setProductDescription(productWithID.getProduct().getProductDescription());
        productEdit.setProductCreated(ZonedDateTime.now());
        productEdit.setProductBrand(productWithID.getProduct().getProductBrand());
        productEdit.setProductPrice(productWithID.getProduct().getProductPrice());
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
