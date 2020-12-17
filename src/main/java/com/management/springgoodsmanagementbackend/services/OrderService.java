package com.management.springgoodsmanagementbackend.services;

import com.management.springgoodsmanagementbackend.model.CartProduct;
import com.management.springgoodsmanagementbackend.model.Ordering;
import com.management.springgoodsmanagementbackend.model.User;
import com.management.springgoodsmanagementbackend.repositories.CartRepository;
import com.management.springgoodsmanagementbackend.repositories.OrderRepository;
import com.management.springgoodsmanagementbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    public List<Ordering> addOrderInfo(Ordering orderingInfo, Integer customerId) {
        Optional<User> userRepositoryById = userRepository.findById(customerId);

        List<CartProduct> allByCustomerId = cartRepository.findAllByCustomerId(customerId);

        System.out.println("allByCustomerId----: " + allByCustomerId);
        List<CartProduct> allByCustomerIdAndOrdered = cartRepository.findAllByCustomerIdAndOrdered(customerId, false);

        userRepositoryById.ifPresent(user -> {
            Ordering ordering = new Ordering();
            ordering.setCustomerName(orderingInfo.getCustomerName());
            ordering.setCustomerAddress(orderingInfo.getCustomerAddress());
            ordering.setCustomerCountry(orderingInfo.getCustomerCountry());
            ordering.setCustomerNumber(orderingInfo.getCustomerNumber());
            ordering.setPaid(orderingInfo.isPaid());
            ordering.setCustomer(user);
            ordering.setCartProductListOrder(allByCustomerIdAndOrdered);

            allByCustomerIdAndOrdered.forEach(cartProduct -> {
                cartProduct.setOrdered(true);
                cartRepository.save(cartProduct);
            });

            orderRepository.save(ordering);
        });
        return orderRepository.findAll();
    }
}
