package com.management.springgoodsmanagementbackend.services;

import com.management.springgoodsmanagementbackend.model.CartProduct;
import com.management.springgoodsmanagementbackend.model.Ordering;
import com.management.springgoodsmanagementbackend.model.User;
import com.management.springgoodsmanagementbackend.repositories.CartRepository;
import com.management.springgoodsmanagementbackend.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AuthService authService;


    public ResponseEntity<String> addOrderInfo(Ordering orderingInfo) {
        Optional<User> authUser = authService.getAuthUser();
        List<CartProduct> allByCustomerIdAndOrdered =
                cartRepository.findAllByCustomerIdAndOrdered(authUser.get().getId(), false);

        authUser.ifPresent(user -> {
            Ordering ordering = new Ordering();
            ordering.setCustomerName(orderingInfo.getCustomerName());
            ordering.setCustomerAddress(orderingInfo.getCustomerAddress());
            ordering.setCustomerCountry(orderingInfo.getCustomerCountry());
            ordering.setCustomerNumber(orderingInfo.getCustomerNumber());
            ordering.setPaid(orderingInfo.isPaid());
            ordering.setCreated(ZonedDateTime.now());
            ordering.setCustomer(user);
            ordering.setCartProductListOrder(allByCustomerIdAndOrdered);

            List<String> listSellerEmailsDuplicated = new ArrayList<>();

            allByCustomerIdAndOrdered.forEach(cartProduct -> {
                cartProduct.setOrdered(true);
                cartRepository.save(cartProduct);
                String sellerEmail = cartProduct.getProduct().getProductSeller().getEmail();
                listSellerEmailsDuplicated.add(sellerEmail);
            });

            List<String> listSellerEmailWithoutDuplicates = listSellerEmailsDuplicated.stream().distinct()
                    .collect(Collectors.toList());

            emailService.sendEmail(listSellerEmailWithoutDuplicates, user);

            orderRepository.save(ordering);
        });
        return ResponseEntity.ok("Ordered!");
    }

    public List<Ordering> getAllOrders() {
        Optional<User> authUser = authService.getAuthUser();
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
                authUser.ifPresent(user -> {
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

    public Integer getOrdersQuantityBySeller() {
            return getAllOrders().size();
    }

    public Ordering getOrder(Integer orderId) {
        Optional<User> authUser = authService.getAuthUser();
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
                authUser.ifPresent(user -> {
                    if (productSeller == user) {
                        newCartProduct.add(cartProduct);
                    } else {
                        System.out.println("Product is from different seller");
                    }
                });
            });
            newCartProduct.forEach(cartProduct -> productService.setImageToProduct(cartProduct.getProduct()));
        });

        return orderNew;
    }

    public Integer deleteOrder(Integer orderId) {
        Optional<User> authUser = authService.getAuthUser();
        Optional<Ordering> orderById = orderRepository.findById(orderId);

        orderById.ifPresent(order -> {

            List<CartProduct> cartProductListOrder = order.getCartProductListOrder();

            List<CartProduct> newCartProduct = new ArrayList<>();

            cartProductListOrder.forEach(cartProduct -> {
                User productSeller = cartProduct.getProduct().getProductSeller();

                authUser.ifPresent(user -> {
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

                authUser.ifPresent(user -> {
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
        return getOrdersQuantityBySeller();
    }
}
