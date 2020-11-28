package com.management.goodsmanagement.services;

import com.management.goodsmanagement.model.Product;
import com.management.goodsmanagement.model.ProductIdWithCustomerId;
import com.management.goodsmanagement.model.User;
import com.management.goodsmanagement.repositories.ProductRepository;
import com.management.goodsmanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class WishlistService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Integer> getAllProductsFromWishlist(Integer loggedUser) {
        System.out.println("Wish!!!!!!!");

        List<Integer> listProductsOfActiveUser = new ArrayList<>();

        Optional<User> activeUser = userRepository.findById(loggedUser);

        activeUser.ifPresent(user ->
                user.getProductsWishList().forEach(
                        product ->
                        listProductsOfActiveUser.add(product.getId())
                ));


        List<Integer> listProductsWithoutDuplicates = listProductsOfActiveUser.stream()
                .distinct()
                .collect(Collectors.toList());

        System.out.println("Without duplicates: " + listProductsWithoutDuplicates);

        return listProductsWithoutDuplicates;

    }


    public List<Product> addProductToWishlist(ProductIdWithCustomerId productIdWithCustomerId) {
        System.out.println("Service" + productIdWithCustomerId);
        Product product = productRepository.findById(productIdWithCustomerId.getProductId());
        Optional<User> user = userRepository.findById(productIdWithCustomerId.getCustomerId());
        user.ifPresent(user1 -> {
//                    user1.getProductsWishList().add(product);
                    user1.addProduct(product);
                    userRepository.save(user1);
                }
        );
        return productRepository.findAll();
    }

    public List<Product> deleteFromWishlist(Integer customerId, Integer productId) {
        System.out.println("Service delete" + customerId + "cust" + productId);
        Optional<Product> productG = productRepository.findById(productId);
        Optional<User> user = userRepository.findById(customerId);
        productG.ifPresent(product2 ->
                        user.ifPresent(user1 -> {
//                    user1.getWishList().remove(product.re);
//                            user1.getProductsWishList().remove(product2);
                            user1.removeProduct(product2);
                            userRepository.save(user1);
                        })
        );
        return productRepository.findAll();

    }
}
