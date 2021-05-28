package com.management.springgoodsmanagementbackend.services;

import com.management.springgoodsmanagementbackend.model.Product;
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

    @Autowired
    private ProductService productService;

    @Autowired
    private AuthService authService;

    public List<Product> getAllProductsFromWishlist() {
        List<Integer> listProductsOfLoggedUser = new ArrayList<>();
        Optional<User> authUser = authService.getAuthUser();
        authUser.ifPresent(user ->
                user.getProductsWishList().forEach(
                        product ->
                                listProductsOfLoggedUser.add(product.getId())
                ));
        List<Integer> listProductsWithoutDuplicates = listProductsOfLoggedUser.stream()
                .distinct()
                .collect(Collectors.toList());

        List<Product> allById = productRepository.findAllById(listProductsWithoutDuplicates);
        allById.forEach(product -> productService.setImageToProduct(product));

        return allById;
    }

    public List<Integer> getAllLikesProductsFromWishlist() {
        List<Integer> listLikesProductsOfActiveUser = new ArrayList<>();
        Optional<User> authUser = authService.getAuthUser();
        authUser.ifPresent(user ->
                user.getProductsWishList().forEach(
                        product ->
                                listLikesProductsOfActiveUser.add(product.getId())
                ));

        return listLikesProductsOfActiveUser.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Integer> addProductToWishlist(Integer productId) {
        Optional<Product> product = productRepository.findById(productId);
        Optional<User> authUser = authService.getAuthUser();

        product.ifPresent(product1 -> authUser.ifPresent(user1 -> {
                    user1.addProduct(product1);
                    userRepository.save(user1);
                }
        ));
        return getAllLikesProductsFromWishlist();
    }

    public List<Integer> removeProductFromWishlist(Integer productId) {
        Optional<Product> product = productRepository.findById(productId);
        Optional<User> authUser = authService.getAuthUser();

        product.ifPresent(product1 ->
                authUser.ifPresent(user1 -> {
                    user1.removeProduct(product1);
                    userRepository.save(user1);
                })
        );
        return getAllLikesProductsFromWishlist();
    }
}
