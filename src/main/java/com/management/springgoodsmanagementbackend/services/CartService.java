package com.management.springgoodsmanagementbackend.services;

import com.management.springgoodsmanagementbackend.dtos.CartDTO;
import com.management.springgoodsmanagementbackend.dtos.CartRequestDTO;
import com.management.springgoodsmanagementbackend.model.CartProduct;
import com.management.springgoodsmanagementbackend.model.Product;
import com.management.springgoodsmanagementbackend.model.User;
import com.management.springgoodsmanagementbackend.repositories.CartRepository;
import com.management.springgoodsmanagementbackend.repositories.ProductRepository;
import com.management.springgoodsmanagementbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<CartProduct> addToCartProductList(CartRequestDTO cartRequestDTO) {

        Optional<CartProduct> byProductIdAndUserId = cartRepository.findByProductIdAndCustomerIdAndOrdered(cartRequestDTO.getProductId(), cartRequestDTO.getUserId(), false);
        Optional<User> userRepositoryById = userRepository.findById(cartRequestDTO.getUserId());
        Optional<Product> productRepositoryById = productRepository.findById(cartRequestDTO.getProductId());

        CartProduct cartProduct = new CartProduct();
        if (!byProductIdAndUserId.isPresent()) {
            userRepositoryById.ifPresent(user -> {
                productRepositoryById.ifPresent(product -> {
                    cartProduct.setCustomer(user);
                    cartProduct.setProduct(product);
                    cartProduct.setQuantity(1);
                    cartRepository.save(cartProduct);
                });
            });
        }

        byProductIdAndUserId.ifPresent(cartProductMain -> {
            cartProductMain.setQuantity(cartProductMain.getQuantity() + 1);
            cartRepository.save(cartProductMain);
        });

        Optional<User> userRepositoryById1 = userRepository.findById(cartRequestDTO.getUserId());
        userRepositoryById1.ifPresent(user -> {
            List<CartProduct> userCartProductList = user.getCartProductList();
            Optional<CartProduct> byProductIdAndUser = cartRepository.findByProductIdAndCustomerIdAndOrdered(cartRequestDTO.getProductId(),
                    cartRequestDTO.getUserId(), false);
            byProductIdAndUser.ifPresent(cartProduct1 -> {
                if (userCartProductList.contains(cartProduct1)) {
                } else {
                    userCartProductList.add(cartProduct1);
                }
            });
            userRepository.save(user);
        });
        return cartRepository.findAll();
    }

    public List<CartDTO> getCartList(Integer userId) {
        Optional<User> userRepositoryById = userRepository.findById(userId);
        List<CartDTO> cartDTOList = new ArrayList<>();
        userRepositoryById.ifPresent(user -> {
            List<CartProduct> cartProductList = user.getCartProductList();
            cartProductList.forEach(cartProduct -> {
                if (!cartProduct.isOrdered()){

                    CartDTO cartDTO = new CartDTO();
                    Product productById = productRepository.findById(cartProduct.getProduct().getId());
                    cartDTO.setProduct(productById);
                    cartDTO.setQuantity(cartProduct.getQuantity());
                    cartDTOList.add(cartDTO);
                }
            });
        });
        return cartDTOList;
    }

    public List<CartProduct> deleteCartItem(Integer userId, Integer productId) {
        Optional<User> userRepositoryById = userRepository.findById(userId);
        Optional<CartProduct> byProductIdAndCustomerIdAndOrdered =
                cartRepository.findByProductIdAndCustomerIdAndOrdered(productId, userId, false);

        byProductIdAndCustomerIdAndOrdered.ifPresent(cartProduct -> {
            userRepositoryById.ifPresent(user -> {
                user.getCartProductList().remove(cartProduct);
                userRepository.save(user);
                cartRepository.delete(cartProduct);
            });
        });

        List<CartProduct> cartProductList = new ArrayList<>();

        userRepositoryById.ifPresent(user -> {
            cartProductList.addAll(user.getCartProductList());
        });

        return cartProductList;
    }
}
