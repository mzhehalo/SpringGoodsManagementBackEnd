package com.management.springgoodsmanagementbackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Main category is required")
    private String mainCategory;
    @NotBlank(message = "Sub category is required")
    private String subCategory;
    @NotBlank(message = "Product Name is required")
    private String productName;
    @NotBlank(message = "Product Description is required")
    @Size(min = 12, max = 100)
    private String productDescription;
    @NotBlank(message = "Product Brand is required")
    @Size(max = 100)
    private String productBrand;
    @Positive(message = "Product price must be positive")
    private int productPrice;
    @NotBlank(message = "Product image is required")
    private String productImgUrl;
    private byte[] productImg;
    @JsonFormat(pattern = "yyyy-MMM-dd HH-mm-ss")
    private ZonedDateTime productCreated;

    @ManyToOne(targetEntity = User.class)
    private User productSeller;

    @JsonIgnore
    @ManyToMany(mappedBy = "productsWishList")
    private List<User> userList;
}
