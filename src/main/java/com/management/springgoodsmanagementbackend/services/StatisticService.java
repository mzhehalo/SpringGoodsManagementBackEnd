package com.management.springgoodsmanagementbackend.services;

import com.management.springgoodsmanagementbackend.dtos.StatisticsDTO;
import com.management.springgoodsmanagementbackend.model.CartProduct;
import com.management.springgoodsmanagementbackend.model.Ordering;
import com.management.springgoodsmanagementbackend.model.Product;
import com.management.springgoodsmanagementbackend.model.User;
import com.management.springgoodsmanagementbackend.repositories.OrderRepository;
import com.management.springgoodsmanagementbackend.repositories.ProductRepository;
import com.management.springgoodsmanagementbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class StatisticService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public StatisticsDTO getStatistics(Integer sellerId) {
        long ordersQuantityAll = orderRepository.count();
        System.out.println(ordersQuantityAll);
        Optional<User> userRepositoryById = userRepository.findById(sellerId);
        List<Ordering> allOrders = orderRepository.findAll();
        List<Ordering> orderList = new ArrayList<>();
        List<Integer> productPriceSeller = new ArrayList<>();
        List<Integer> productPriceAll = new ArrayList<>();
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
                int productPriceForAll = cartProduct.getProduct().getProductPrice();
                productPriceAll.add(productPriceForAll);
                userRepositoryById.ifPresent(user -> {
                    if (productSeller == user) {
                        newCartProduct.add(cartProduct);
                        int productPrice1 = cartProduct.getProduct().getProductPrice();
                        productPriceSeller.add(productPrice1);
                    } else {
                        System.out.println("Product is from different seller");
                    }
                });
            });
            if (!orderNew.getCartProductListOrder().isEmpty()) {
                orderList.add(orderNew);
            }
        });

        long orderListQuantity = orderList.size();
        System.out.println(orderListQuantity);

        long numberOfProductsAll = productRepository.count();

        User userRepositoryById1 = userRepository.findById(sellerId).get();

        int numberOfProducts = userRepositoryById1.getProductList().size();

        int sumOrderedAll = productPriceAll.stream().mapToInt(Integer::intValue).sum();
        int sumOrdered = productPriceSeller.stream().mapToInt(Integer::intValue).sum();

        List<Product> allProducts = productRepository.findAll();
        List<Integer> allPriceProducts = new ArrayList<>();
        List<Integer> sellerPriceProducts = new ArrayList<>();

        allProducts.forEach(product -> {
            allPriceProducts.add(product.getProductPrice());
            if (product.getProductSeller().equals(userRepositoryById1)) {
                sellerPriceProducts.add(product.getProductPrice());
            }
        });

        int allSumPriceProducts = allPriceProducts.stream().mapToInt(Integer::intValue).sum();
        int sellerSumPriceProducts = sellerPriceProducts.stream().mapToInt(Integer::intValue).sum();

        StatisticsDTO statisticsDTO = new StatisticsDTO();
        statisticsDTO.setQuantityOrders(orderListQuantity);
        statisticsDTO.setQuantityOrdersAll(ordersQuantityAll);
        statisticsDTO.setNumberOfProductsAll(numberOfProductsAll);
        statisticsDTO.setNumberOfProducts(numberOfProducts);
        statisticsDTO.setSumPriceOrderedAll(sumOrderedAll);
        statisticsDTO.setSumPriceOrdered(sumOrdered);
        statisticsDTO.setSumPriceAll(allSumPriceProducts);
        statisticsDTO.setSumPrice(sellerSumPriceProducts);

        return statisticsDTO;
    }
}
