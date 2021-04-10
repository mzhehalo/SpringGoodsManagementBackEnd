package com.management.springgoodsmanagementbackend.services;

import com.management.springgoodsmanagementbackend.model.CartProduct;
import com.management.springgoodsmanagementbackend.model.Ordering;
import com.management.springgoodsmanagementbackend.model.User;
import com.management.springgoodsmanagementbackend.repositories.CartRepository;
import com.management.springgoodsmanagementbackend.repositories.OrderRepository;
import com.management.springgoodsmanagementbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
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

        List<CartProduct> allByCustomerIdAndOrdered = cartRepository.findAllByCustomerIdAndOrdered(customerId, false);

        userRepositoryById.ifPresent(user -> {
            Ordering ordering = new Ordering();
            ordering.setCustomerName(orderingInfo.getCustomerName());
            ordering.setCustomerAddress(orderingInfo.getCustomerAddress());
            ordering.setCustomerCountry(orderingInfo.getCustomerCountry());
            ordering.setCustomerNumber(orderingInfo.getCustomerNumber());
            ordering.setPaid(orderingInfo.isPaid());
            ordering.setCreated(ZonedDateTime.now());
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

    public List<Ordering> getOrders(Integer sellerId) {
        Optional<User> userRepositoryById = userRepository.findById(sellerId);
        List<Ordering> allOrders = orderRepository.findAll();
        List<Ordering> orderList = new ArrayList<>();
        allOrders.forEach(order -> {
            List<CartProduct> cartProductListOrder = order.getCartProductListOrder();

            List<CartProduct> newCartProduct = new ArrayList<>();

            Ordering orderNew = new Ordering();

            orderNew.setId(order.getId());
            orderNew.setCustomerName(order.getCustomerName());
            orderNew.setCustomerAddress(order.getCustomerAddress());
            orderNew.setCustomerCountry(order.getCustomerCountry());
            orderNew.setCustomerNumber(order.getCustomerNumber());
            orderNew.setPaid(order.isPaid());
            orderNew.setCustomer(order.getCustomer());
            orderNew.setCreated(order.getCreated());
            orderNew.setCartProductListOrder(newCartProduct);

            cartProductListOrder.forEach(cartProduct -> {
                User productSeller = cartProduct.getProduct().getProductSeller();
                userRepositoryById.ifPresent(user -> {
                    if (productSeller == user) {
                        newCartProduct.add(cartProduct);
                    } else {
                        System.out.println("Product is from different seller");
                    }
                });
            });
            if (!orderNew.getCartProductListOrder().isEmpty()) {
                orderList.add(orderNew);
            }
        });
        return orderList;
    }

    public Ordering getOrder(Integer sellerId, Integer orderId) {

        Optional<User> userRepositoryById = userRepository.findById(sellerId);
        Optional<Ordering> orderById = orderRepository.findById(orderId);

        Ordering orderNew = new Ordering();

        orderById.ifPresent(order -> {

            List<CartProduct> cartProductListOrder = order.getCartProductListOrder();

            List<CartProduct> newCartProduct = new ArrayList<>();

            orderNew.setId(order.getId());
            orderNew.setCustomerName(order.getCustomerName());
            orderNew.setCustomerAddress(order.getCustomerAddress());
            orderNew.setCustomerCountry(order.getCustomerCountry());
            orderNew.setCustomerNumber(order.getCustomerNumber());
            orderNew.setPaid(order.isPaid());
            orderNew.setCustomer(order.getCustomer());
            orderNew.setCreated(order.getCreated());
            orderNew.setCartProductListOrder(newCartProduct);

            cartProductListOrder.forEach(cartProduct -> {
                User productSeller = cartProduct.getProduct().getProductSeller();
                userRepositoryById.ifPresent(user -> {
                    if (productSeller == user) {
                        newCartProduct.add(cartProduct);
                    } else {
                        System.out.println("Product is from different seller");
                    }
                });
            });
        });

        return orderNew;
    }

    public void deleteOrder(Integer sellerId, Integer orderId) {
        Optional<User> userRepositoryById = userRepository.findById(sellerId);
        Optional<Ordering> orderById = orderRepository.findById(orderId);

        orderById.ifPresent(order -> {

            List<CartProduct> cartProductListOrder = order.getCartProductListOrder();

            List<CartProduct> newCartProduct = new ArrayList<>();

            cartProductListOrder.forEach(cartProduct -> {
                User productSeller = cartProduct.getProduct().getProductSeller();

                userRepositoryById.ifPresent(user -> {
                    if (productSeller != user) {
                        newCartProduct.add(cartProduct);
                    } else {
                        System.out.println("They are not same!");
                    }
                });
            });
            order.setCartProductListOrder(newCartProduct);
            orderRepository.save(order);

            cartProductListOrder.forEach(cartProduct -> {
                User productSeller = cartProduct.getProduct().getProductSeller();

                userRepositoryById.ifPresent(user -> {
                    if (productSeller == user) {
                        cartRepository.delete(cartProduct);
                    } else {
                        System.out.println("They are not same!");
                    }
                });
            });

            if (order.getCartProductListOrder().isEmpty()) {
                orderRepository.delete(order);
            }
        });
    }
}
