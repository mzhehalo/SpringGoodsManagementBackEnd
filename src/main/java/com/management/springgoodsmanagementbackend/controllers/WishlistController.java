package com.management.springgoodsmanagementbackend.controllers;

import com.management.springgoodsmanagementbackend.model.Product;
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

    @RequestMapping(path = "/get-products", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getAllProductsFromWishlist() {
        return wishlistService.getAllProductsFromWishlist();
    }

    @RequestMapping(path = "/get-likes", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Integer> getAllLikesProductsFromWishlist() {
        return wishlistService.getAllLikesProductsFromWishlist();
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public List<Integer> addProductToWishlist(@RequestBody Integer productId) {
        return wishlistService.addProductToWishlist(productId);
    }

    @RequestMapping(path = "/delete/{productId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public List<Integer> removeProductFromWishlist(@PathVariable Integer productId) {
        return wishlistService.removeProductFromWishlist(productId);
    }
}
