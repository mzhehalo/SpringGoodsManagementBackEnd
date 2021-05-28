package com.management.springgoodsmanagementbackend.controllers;

import com.management.springgoodsmanagementbackend.dtos.CartDTO;
import com.management.springgoodsmanagementbackend.model.CartProduct;
import com.management.springgoodsmanagementbackend.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4300")
@RequestMapping(path = "/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public List<CartDTO> addToCartProduct(@RequestBody Integer productId) {
        return cartService.addToCartProduct(productId);
    }

    @RequestMapping(path = "/get", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CartDTO> getCartList() {
        return cartService.getCartList();
    }

    @RequestMapping(path = "/delete/{productId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public List<CartProduct> deleteCartItem(@PathVariable Integer productId){
        return this.cartService.deleteCartItem(productId);
    }
}
