package com.management.springgoodsmanagementbackend.services;

import com.management.springgoodsmanagementbackend.dtos.ProductPageDTO;
import com.management.springgoodsmanagementbackend.model.CartProduct;
import com.management.springgoodsmanagementbackend.model.Ordering;
import com.management.springgoodsmanagementbackend.model.Product;
import com.management.springgoodsmanagementbackend.model.User;
import com.management.springgoodsmanagementbackend.repositories.CartRepository;
import com.management.springgoodsmanagementbackend.repositories.OrderRepository;
import com.management.springgoodsmanagementbackend.repositories.ProductRepository;
import com.management.springgoodsmanagementbackend.repositories.UserRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.ZonedDateTime;
import java.util.Arrays;
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

    @Autowired
    private OrderRepository orderRepository;

    public ResponseEntity addProduct(MultipartFile file, Product product, Integer sellerId) {
        product.setProductCreated(ZonedDateTime.now());
        userRepository.findById(sellerId).ifPresent(product::setProductSeller);

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        Path resourceDirectory = Paths.get("src", "main", "resources", "productImages");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();

        if (!Files.exists(resourceDirectory)) {
            try {
                Files.createDirectories(resourceDirectory);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Path destination = Paths.get(resourceDirectory.toString() + "\\" + fileName);

        try {
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();
        }

        product.setProductImgUrl(absolutePath + "\\" + fileName);

        productRepository.save(product);

        return ResponseEntity.ok("Ok I did It!!!");
    }

    public ProductPageDTO getProducts(PageRequest pageRequest, Integer priceMin, Integer priceMax) {

        Page<Product> productPage = productRepository
                .findProductsByProductPriceLessThanAndProductPriceGreaterThan(priceMax, priceMin, pageRequest);

        ProductPageDTO productPageDTO = new ProductPageDTO();

        List<Product> content = productPage.getContent();
        content.forEach(this::setImageToProduct);

        productPageDTO.setProductList(content);
        productPageDTO.setNumber(productPage.getNumber());
        productPageDTO.setSize(productPage.getSize());
        productPageDTO.setTotalElements(productPage.getTotalElements());
        return productPageDTO;
    }

    public Optional<Product> getProductById(Integer id) {
        Optional<Product> productRepositoryById = productRepository.findById(id);
        productRepositoryById.ifPresent(this::setImageToProduct);
        return productRepositoryById;
    }

    public ResponseEntity editProduct(MultipartFile file, Product product, Integer productId) {
        Optional<Product> productRepositoryById = productRepository.findById(productId);

        productRepositoryById.ifPresent(product1 -> {
            product1.setProductName(product.getProductName());
            product1.setProductDescription(product.getProductDescription());
            product1.setProductCreated(ZonedDateTime.now());
            product1.setProductBrand(product.getProductBrand());
            product1.setProductPrice(product.getProductPrice());
            product1.setMainCategory(product.getMainCategory());
            product1.setSubCategory(product.getSubCategory());

            String productImgUrl = product1.getProductImgUrl();
            File existFile = new File(productImgUrl);

            if (existFile.exists()) {
                existFile.delete();
            }

            String fileName = StringUtils.cleanPath(file.getOriginalFilename());

            Path resourceDirectory = Paths.get("src", "main", "resources", "productImages");
            String absolutePath = resourceDirectory.toFile().getAbsolutePath();

            Path destination = Paths.get(resourceDirectory.toString() + "\\" + fileName);

            try {
                Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }

            product1.setProductImgUrl(absolutePath + "\\" + fileName);

            productRepository.save(product1);
        });

        return ResponseEntity.ok("Ok I did It!!!");
    }

    public void deleteProduct(Integer productId) {
        Optional<Product> productbyId = productRepository.findById(productId);
        List<User> users = userRepository.findAll();
        List<CartProduct> allByProductId = cartRepository.findAllByProductId(productId);

        productbyId.ifPresent(product -> {
            Path destination = Paths.get(product.getProductImgUrl());

            try {
                Files.delete(destination);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        for (User user : users) {
            productbyId.ifPresent(product ->
            {
                user.getProductsWishList().remove(product);

            });
            user.getCartProductList().removeAll(allByProductId);
            List<Ordering> orderList = orderRepository.findAll();
            orderList.forEach(ordering -> {
                ordering.getCartProductListOrder().removeAll(allByProductId);
                orderRepository.save(ordering);
            });
            cartRepository.deleteAll(allByProductId);
        }
        productRepository.deleteById(productId);
    }

    public ProductPageDTO getProductByCategory(String mainCategory, String subCategory, PageRequest pageRequest) {
        ProductPageDTO productPageDTO = new ProductPageDTO();

        if (!mainCategory.equals(subCategory)) {
            Page<Product> productPage = productRepository.findByMainCategoryAndSubCategory(mainCategory, subCategory, pageRequest);

            List<Product> content = productPage.getContent();
            content.forEach(this::setImageToProduct);

            productPageDTO.setProductList(content);
            productPageDTO.setNumber(productPage.getNumber());
            productPageDTO.setSize(productPage.getSize());
            productPageDTO.setTotalElements(productPage.getTotalElements());
        } else {
            Page<Product> productPage = productRepository.findByMainCategory(mainCategory, pageRequest);

            List<Product> content = productPage.getContent();
            content.forEach(this::setImageToProduct);

            productPageDTO.setProductList(content);
            productPageDTO.setNumber(productPage.getNumber());
            productPageDTO.setSize(productPage.getSize());
            productPageDTO.setTotalElements(productPage.getTotalElements());
        }
        return productPageDTO;
    }

    public void setImageToProduct(Product product) {
        Path destination = Paths.get(product.getProductImgUrl());

        try {
            byte[] productImgBytes = IOUtils.toByteArray(destination.toUri());
            product.setProductImg(productImgBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Integer> getMinMax() {
        Integer firstByOrderByProductPriceAsc = productRepository.findFirstByOrderByProductPriceAsc().getProductPrice();
        Integer firstByOrderByProductPriceDesc = productRepository.findFirstByOrderByProductPriceDesc().getProductPrice();
        return Arrays.asList(firstByOrderByProductPriceAsc, firstByOrderByProductPriceDesc);
    }
}
