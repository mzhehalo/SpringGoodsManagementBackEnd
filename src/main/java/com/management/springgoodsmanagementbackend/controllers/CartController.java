package com.management.springgoodsmanagementbackend.controllers;

import com.management.springgoodsmanagementbackend.dtos.CartDTO;
import com.management.springgoodsmanagementbackend.dtos.CartRequestDTO;
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
    public List<CartProduct> addToCartProductList(@RequestBody CartRequestDTO cartRequestDTO){
        return cartService.addToCartProductList(cartRequestDTO);
    }

    @RequestMapping(path = "/get/{userId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CartDTO> getCartList(@PathVariable Integer userId){
        return cartService.getCartList(userId);
    }

    @RequestMapping(path = "/delete/{userId}/{productId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public List<CartProduct> deleteCartItem(@PathVariable Integer userId, @PathVariable Integer productId){
        return this.cartService.deleteCartItem(userId, productId);
    }
}
