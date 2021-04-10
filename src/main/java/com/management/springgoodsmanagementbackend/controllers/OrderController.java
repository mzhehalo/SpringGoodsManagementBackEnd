package com.management.springgoodsmanagementbackend.controllers;

import com.management.springgoodsmanagementbackend.model.Ordering;
import com.management.springgoodsmanagementbackend.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4300")
@RequestMapping(path = "/order-info")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(path = "/add/{customerId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public List<Ordering> addOrderInfo(@RequestBody Ordering orderingInfo, @PathVariable Integer customerId){
        return orderService.addOrderInfo(orderingInfo, customerId);
    }

    @RequestMapping(path = "/get/orders/{sellerId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Ordering> getOrders(@PathVariable Integer sellerId){
        return orderService.getOrders(sellerId);
    }

    @RequestMapping(path = "/get/order/{sellerId}/{orderId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Ordering getOrder(@PathVariable Integer sellerId, @PathVariable Integer orderId){
        return orderService.getOrder(sellerId, orderId);
    }

    @RequestMapping(path = "/delete/{sellerId}/{orderId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteOrder(@PathVariable Integer sellerId, @PathVariable Integer orderId){
        this.orderService.deleteOrder(sellerId, orderId);
    }
}
