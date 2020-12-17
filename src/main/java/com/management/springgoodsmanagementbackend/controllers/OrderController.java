package com.management.springgoodsmanagementbackend.controllers;

import com.management.springgoodsmanagementbackend.model.Ordering;
import com.management.springgoodsmanagementbackend.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4300")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(path = "/order-info/add/{customerId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public List<Ordering> addOrderInfo(@RequestBody Ordering orderingInfo, @PathVariable Integer customerId){
        return orderService.addOrderInfo(orderingInfo, customerId);
    }
}
