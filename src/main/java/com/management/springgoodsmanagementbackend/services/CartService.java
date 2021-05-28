package com.management.springgoodsmanagementbackend.services;

import com.management.springgoodsmanagementbackend.dtos.CartDTO;
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

    @Autowired
    private AuthService authService;

    public List<CartDTO> addToCartProduct(Integer productId) {
        Optional<User> authUser = authService.getAuthUser();
        Optional<CartProduct> byProductIdAndUserId =
                cartRepository.findByProductIdAndCustomerIdAndOrdered(productId, authUser.get().getId(), false);
        Optional<Product> productRepositoryById = productRepository.findById(productId);

        CartProduct cartProduct = new CartProduct();
        if (!byProductIdAndUserId.isPresent()) {
            authUser.ifPresent(user -> productRepositoryById.ifPresent(product -> {
                cartProduct.setCustomer(user);
                cartProduct.setProduct(product);
                cartProduct.setQuantity(1);
                cartRepository.save(cartProduct);
            }));
        }

        byProductIdAndUserId.ifPresent(cartProductMain -> {
            cartProductMain.setQuantity(cartProductMain.getQuantity() + 1);
            cartRepository.save(cartProductMain);
        });

        authUser.ifPresent(user -> {
            List<CartProduct> userCartProductList = user.getCartProductList();
            Optional<CartProduct> byProductIdAndUser = cartRepository.findByProductIdAndCustomerIdAndOrdered(productId,
                    authUser.get().getId(), false);
            byProductIdAndUser.ifPresent(cartProduct1 -> {
                if (!userCartProductList.contains(cartProduct1)) {
                    userCartProductList.add(cartProduct1);
                }
            });
            userRepository.save(user);
        });
        return getCartList();
    }

    public List<CartDTO> getCartList() {
        Optional<User> authUser = authService.getAuthUser();
        List<CartDTO> cartDTOList = new ArrayList<>();
        authUser.ifPresent(user -> {
            List<CartProduct> cartProductList = user.getCartProductList();
            cartProductList.forEach(cartProduct -> {
                if (!cartProduct.isOrdered()) {

                    CartDTO cartDTO = new CartDTO();
                    cartDTO.setProduct(cartProduct.getProduct());
                    cartDTO.setQuantity(cartProduct.getQuantity());
                    cartDTOList.add(cartDTO);
                }
            });
        });
        return cartDTOList;
    }

    public List<CartProduct> deleteCartItem(Integer productId) {
        Optional<User> authUser = authService.getAuthUser();
        Optional<CartProduct> byProductIdAndCustomerIdAndOrdered =
                cartRepository.findByProductIdAndCustomerIdAndOrdered(productId,
                        authUser.get().getId(),
                        false);

        byProductIdAndCustomerIdAndOrdered.ifPresent(cartProduct -> authUser.ifPresent(user -> {
            user.getCartProductList().remove(cartProduct);
            userRepository.save(user);
            cartRepository.delete(cartProduct);
        }));

        List<CartProduct> cartProductList = new ArrayList<>();

        authUser.ifPresent(user -> cartProductList.addAll(user.getCartProductList()));

        return cartProductList;
    }
}
