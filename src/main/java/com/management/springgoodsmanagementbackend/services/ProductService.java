package com.management.springgoodsmanagementbackend.services;

import com.management.springgoodsmanagementbackend.dtos.PriceMinMaxDTO;
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
import org.springframework.http.HttpStatus;
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
import java.util.List;
import java.util.Objects;
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

    @Autowired
    private AuthService authService;

    public ResponseEntity<String> addProduct(MultipartFile file, Product product) {
        Product createdProduct = new Product();

        Optional<User> authUser = authService.getAuthUser();

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Path resourceDirectory = Paths.get("src", "main", "resources", "productImages");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();

        if (!Files.exists(resourceDirectory)) {
            try {
                Files.createDirectories(resourceDirectory);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Path destination = Paths.get(resourceDirectory + "\\" + fileName);

        try {
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        createdProduct.setProductName(product.getProductName());
        createdProduct.setProductPrice(product.getProductPrice());
        createdProduct.setProductBrand(product.getProductBrand());
        createdProduct.setProductCreated(ZonedDateTime.now());
        createdProduct.setMainCategory(product.getMainCategory());
        createdProduct.setSubCategory(product.getSubCategory());
        createdProduct.setProductDescription(product.getProductDescription());
        createdProduct.setProductImgUrl(absolutePath + "\\" + fileName);
        userRepository.findById(authUser.get().getId()).ifPresent(createdProduct::setProductSeller);

        productRepository.save(createdProduct);

        return ResponseEntity.ok("Product Added!");
    }

    public ProductPageDTO getProducts(PageRequest pageRequest, Integer priceMin, Integer priceMax) {

        Page<Product> productPage = productRepository
                .findProductsByProductPriceLessThanAndProductPriceGreaterThan(priceMax + 1, priceMin - 1, pageRequest);

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

    public ResponseEntity<HttpStatus> editProduct(MultipartFile file, Product product, Integer productId) {
        Optional<Product> productById = productRepository.findById(productId);
        Optional<User> authUser = authService.getAuthUser();
        if (productById.get().getProductSeller().getId() == authUser.get().getId() || authUser.get().getRole().equals("ROLE_ADMIN")) {

            productById.ifPresent(product1 -> {
                product1.setProductName(product.getProductName());
                product1.setProductDescription(product.getProductDescription());
                product1.setProductCreated(ZonedDateTime.now());
                product1.setProductBrand(product.getProductBrand());
                product1.setProductPrice(product.getProductPrice());
                product1.setMainCategory(product.getMainCategory());
                product1.setSubCategory(product.getSubCategory());

                String productImgUrl = product1.getProductImgUrl();
                File existFile = new File(productImgUrl);

                int sizeImgProducts = productRepository.findByProductImgUrl(productImgUrl).size();
                if (existFile.exists() && sizeImgProducts < 2) {
                    existFile.delete();
                }

                String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

                Path resourceDirectory = Paths.get("src", "main", "resources", "productImages");
                String absolutePath = resourceDirectory.toFile().getAbsolutePath();

                Path destination = Paths.get(resourceDirectory + "\\" + fileName);

                try {
                    Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                product1.setProductImgUrl(absolutePath + "\\" + fileName);

                productRepository.save(product1);
            });

            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    public ResponseEntity<HttpStatus> deleteProduct(Integer productId) {
        Optional<Product> productById = productRepository.findById(productId);
        Optional<User> authUser = authService.getAuthUser();
        if (productById.get().getProductSeller().getId() == authUser.get().getId() || authUser.get().getRole().equals("ROLE_ADMIN")) {
            List<User> allUsers = userRepository.findAll();
            List<CartProduct> cartRepositoryAllByProductId = cartRepository.findAllByProductId(productId);

            productById.ifPresent(product -> {
                Path destination = Paths.get(product.getProductImgUrl());
                int sizeImgProducts = productRepository.findByProductImgUrl(product.getProductImgUrl()).size();
                if (sizeImgProducts < 2) {
                    try {
                        Files.delete(destination);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            for (User user : allUsers) {
                productById.ifPresent(product ->
                        user.getProductsWishList().remove(product));
                user.getCartProductList().removeAll(cartRepositoryAllByProductId);
                List<Ordering> orderList = orderRepository.findAll();
                orderList.forEach(ordering -> {
                    ordering.getCartProductListOrder().removeAll(cartRepositoryAllByProductId);
                    orderRepository.save(ordering);
                });
                cartRepository.deleteAll(cartRepositoryAllByProductId);
            }
            productRepository.deleteById(productId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    public ProductPageDTO getProductByCategory(String mainCategory,
                                               String subCategory,
                                               PageRequest pageRequest,
                                               Integer priceMin,
                                               Integer priceMax) {
        ProductPageDTO productPageDTO = new ProductPageDTO();

        if (!mainCategory.equals(subCategory)) {
            Page<Product> productPage = productRepository.
                    findByMainCategoryAndSubCategoryAndProductPriceLessThanAndProductPriceGreaterThan(mainCategory,
                            subCategory,
                            priceMax + 1,
                            priceMin - 1,
                            pageRequest);

            List<Product> content = productPage.getContent();
            content.forEach(this::setImageToProduct);

            productPageDTO.setProductList(content);
            productPageDTO.setNumber(productPage.getNumber());
            productPageDTO.setSize(productPage.getSize());
            productPageDTO.setTotalElements(productPage.getTotalElements());
        } else {
            Page<Product> productPage = productRepository
                    .findByMainCategoryAndProductPriceLessThanAndProductPriceGreaterThan(mainCategory,
                            priceMax,
                            priceMin,
                            pageRequest);

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

    public PriceMinMaxDTO getMinMax(String mainCategory, String subCategory) {
        PriceMinMaxDTO priceMinMaxDTO = new PriceMinMaxDTO();

        if (mainCategory.equals("undefined") && subCategory.equals("undefined")) {

            Optional<Product> productPriceMin = productRepository.findFirstByOrderByProductPriceAsc();
            productPriceMin.ifPresent(product -> priceMinMaxDTO.setPriceMin(product.getProductPrice()));

            Optional<Product> productPriceMax = productRepository.findFirstByOrderByProductPriceDesc();
            productPriceMax.ifPresent(product -> priceMinMaxDTO.setPriceMax(product.getProductPrice()));
            if (!productPriceMin.isPresent() && !productPriceMax.isPresent()){
                priceMinMaxDTO.setPriceMin(0);
                priceMinMaxDTO.setPriceMax(0);
            }
        } else if (mainCategory.equals(subCategory)) {
            Optional<Product> productPriceMin = productRepository.findFirstByMainCategoryOrderByProductPriceAsc(mainCategory);
            productPriceMin.ifPresent(product -> priceMinMaxDTO.setPriceMin(product.getProductPrice()));

            Optional<Product> productPriceMax = productRepository.findFirstByMainCategoryOrderByProductPriceDesc(mainCategory);
            productPriceMax.ifPresent(product -> priceMinMaxDTO.setPriceMax(product.getProductPrice()));
            if (!productPriceMin.isPresent() && !productPriceMax.isPresent()){
                priceMinMaxDTO.setPriceMin(0);
                priceMinMaxDTO.setPriceMax(0);
            }
        } else {

            Optional<Product> productPriceMin =
                    productRepository.findFirstByMainCategoryAndSubCategoryOrderByProductPriceAsc(mainCategory, subCategory);
            productPriceMin.ifPresent(product -> priceMinMaxDTO.setPriceMin(product.getProductPrice()));


            Optional<Product> productPriceMax =
                    productRepository.findFirstByMainCategoryAndSubCategoryOrderByProductPriceDesc(mainCategory, subCategory);
            productPriceMax.ifPresent(product -> priceMinMaxDTO.setPriceMax(product.getProductPrice()));

            if (!productPriceMin.isPresent() && !productPriceMax.isPresent()){
                priceMinMaxDTO.setPriceMin(0);
                priceMinMaxDTO.setPriceMax(0);
            }
        }
        return priceMinMaxDTO;
    }
}
