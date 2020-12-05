package com.management.springgoodsmanagementbackend.services;

import com.management.springgoodsmanagementbackend.model.Product;
import com.management.springgoodsmanagementbackend.dtos.UserIdWithProductIdDTO;
import com.management.springgoodsmanagementbackend.model.User;
import com.management.springgoodsmanagementbackend.repositories.ProductRepository;
import com.management.springgoodsmanagementbackend.repositories.UserRepository;
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

    public List<Product> getAllProductsFromWishlist(Integer loggedUser) {
        List<Integer> listProductsOfLoggedUser = new ArrayList<>();
        Optional<User> activeUser = userRepository.findById(loggedUser);
        activeUser.ifPresent(user ->
                user.getProductsWishList().forEach(
                        product ->
                                listProductsOfLoggedUser.add(product.getId())
                ));
        List<Integer> listProductsWithoutDuplicates = listProductsOfLoggedUser.stream()
                .distinct()
                .collect(Collectors.toList());
        return productRepository.findAllById(listProductsWithoutDuplicates);
    }

    public List<Integer> getAllLikesProductsFromWishlist(Integer loggedUser) {
        System.out.println("Wish!!!!!!!");

        List<Integer> listLikesProductsOfActiveUser = new ArrayList<>();
        Optional<User> activeUser = userRepository.findById(loggedUser);
        activeUser.ifPresent(user ->
                user.getProductsWishList().forEach(
                        product ->
                        listLikesProductsOfActiveUser.add(product.getId())
                ));
        List<Integer> listProductsWithoutDuplicates = listLikesProductsOfActiveUser.stream()
                .distinct()
                .collect(Collectors.toList());
        System.out.println("Without duplicates: " + listProductsWithoutDuplicates);

        return listProductsWithoutDuplicates;

    }


    public List<Product> addProductToWishlist(UserIdWithProductIdDTO userIdWithProductIdDTO) {
        System.out.println("Service" + userIdWithProductIdDTO);
        Product product = productRepository.findById(userIdWithProductIdDTO.getProductId());
        Optional<User> user = userRepository.findById(userIdWithProductIdDTO.getUserId());
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
