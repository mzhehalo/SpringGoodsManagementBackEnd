package com.management.springgoodsmanagementbackend.controllers;

import com.management.springgoodsmanagementbackend.model.Ordering;
import com.management.springgoodsmanagementbackend.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4300")
@RequestMapping(path = "/order-info")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public ResponseEntity<String> addOrderInfo(@RequestBody @Valid Ordering orderingInfo) {
        return orderService.addOrderInfo(orderingInfo);
    }

    @RequestMapping(path = "/get/orders", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Ordering> getAllOrders() {
        return orderService.getAllOrders();
    }

    @RequestMapping(path = "/get/orders/quantity", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Integer getOrdersQuantityBySeller() {
        return orderService.getOrdersQuantityBySeller();
    }

    @RequestMapping(path = "/get/order/{orderId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Ordering getOrder(@PathVariable Integer orderId) {
        return orderService.getOrder(orderId);
    }

    @RequestMapping(path = "/delete/{orderId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public Integer deleteOrder(@PathVariable Integer orderId) {
       return this.orderService.deleteOrder(orderId);
    }
}
