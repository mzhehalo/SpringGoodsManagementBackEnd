package com.management.goodsmanagement.controllers;

import com.management.goodsmanagement.model.Product;
import com.management.goodsmanagement.model.ProductIdWithCustomerId;
import com.management.goodsmanagement.model.User;
import com.management.goodsmanagement.services.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4300")
@RequestMapping(path = "/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @RequestMapping(path = "/get/{loggedUser}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Integer> getAllProductsFromWishlist(@PathVariable Integer loggedUser){
        System.out.println("loggedUser" + loggedUser);
        return wishlistService.getAllProductsFromWishlist(loggedUser);
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public List<Product> addProductToWishlist(@RequestBody ProductIdWithCustomerId productIdWithCustomerId) {
        System.out.println("Controller" + productIdWithCustomerId);
        return wishlistService.addProductToWishlist(productIdWithCustomerId);
    }

    @RequestMapping(path = "delete/{customerId}/{productId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public List<Product> deleteProductFromWishlist(@PathVariable Integer customerId, @PathVariable Integer productId){
        System.out.println("Controller delete" + customerId + "cust" + productId);
        return wishlistService.deleteFromWishlist(customerId, productId);

    }
}
