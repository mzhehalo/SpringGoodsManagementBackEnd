package com.management.springgoodsmanagementbackend.services;

import com.management.springgoodsmanagementbackend.dtos.ProductPageDTO;
import com.management.springgoodsmanagementbackend.model.CartProduct;
import com.management.springgoodsmanagementbackend.model.Product;
import com.management.springgoodsmanagementbackend.dtos.ProductWithIdDTO;
import com.management.springgoodsmanagementbackend.model.User;
import com.management.springgoodsmanagementbackend.repositories.CartRepository;
import com.management.springgoodsmanagementbackend.repositories.ProductRepository;
import com.management.springgoodsmanagementbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    public Product addProduct(ProductWithIdDTO productWithIdDTO) {
        System.out.println("Service 1:" + productWithIdDTO);
        productWithIdDTO.getProduct().setProductCreated(ZonedDateTime.now());
        userRepository.findById(productWithIdDTO.getId()).ifPresent(user -> {
            productWithIdDTO.getProduct().setProductSeller(user);
        });
        System.out.println("Service 2:" + productWithIdDTO);
        return productRepository.save(productWithIdDTO.getProduct());
    }

    public ProductPageDTO getProducts(PageRequest pageRequest) {
        Page<Product> productPage = productRepository.findAll(pageRequest);
        ProductPageDTO productPageDTO = new ProductPageDTO();
        productPageDTO.setProductList(productPage.getContent());
        productPageDTO.setNumber(productPage.getNumber());
        productPageDTO.setSize(productPage.getSize());
        productPageDTO.setTotalElements(productPage.getTotalElements());
        System.out.println("pageRequest-----: " + productPage);
        return productPageDTO;
    }

    public Optional<Product> getProductById(Integer id) {
        return productRepository.findById(id);
    }

    public Product editProduct(ProductWithIdDTO productWithIdDTO) {
        Product productEdit = productRepository.findById(productWithIdDTO.getId());

        productEdit.setProductName(productWithIdDTO.getProduct().getProductName());
        productEdit.setProductDescription(productWithIdDTO.getProduct().getProductDescription());
        productEdit.setProductCreated(ZonedDateTime.now());
        productEdit.setProductBrand(productWithIdDTO.getProduct().getProductBrand());
        productEdit.setProductPrice(productWithIdDTO.getProduct().getProductPrice());
            return productRepository.save(productEdit);

    }

    public void deleteProduct(Integer id) {
        Optional<Product> productbyId = productRepository.findById(id);
        List<User> users = userRepository.findAll();
        List<CartProduct> allByProductId = cartRepository.findAllByProductId(id);
        System.out.println("allByProductId-------: " + allByProductId);

        for (User user: users){
            productbyId.ifPresent(product ->
            user.getProductsWishList().remove(product));
            user.getCartProductList().removeAll(allByProductId);
            cartRepository.deleteAll(allByProductId);
        }
        productRepository.deleteById(id);
    }
}
