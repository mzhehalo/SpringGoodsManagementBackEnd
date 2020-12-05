package com.management.springgoodsmanagementbackend.controllers;

import com.management.springgoodsmanagementbackend.model.Product;
import com.management.springgoodsmanagementbackend.dtos.UserIdWithProductIdDTO;
import com.management.springgoodsmanagementbackend.services.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4300")
@RequestMapping(path = "/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @RequestMapping(path = "/get-products/{loggedUser}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getAllProductsFromWishlist(@PathVariable Integer loggedUser){
        return wishlistService.getAllProductsFromWishlist(loggedUser);
    }

    @RequestMapping(path = "/get-likes/{loggedUser}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Integer> getAllLikesProductsFromWishlist(@PathVariable Integer loggedUser){
        System.out.println("loggedUser" + loggedUser);
        return wishlistService.getAllLikesProductsFromWishlist(loggedUser);
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public List<Product> addProductToWishlist(@RequestBody UserIdWithProductIdDTO userIdWithProductIdDTO) {
        System.out.println("Controller" + userIdWithProductIdDTO);
        return wishlistService.addProductToWishlist(userIdWithProductIdDTO);
    }

    @RequestMapping(path = "delete/{customerId}/{productId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public List<Product> deleteProductFromWishlist(@PathVariable Integer customerId, @PathVariable Integer productId){
        System.out.println("Controller delete" + customerId + "cust" + productId);
        return wishlistService.deleteFromWishlist(customerId, productId);

    }
}
