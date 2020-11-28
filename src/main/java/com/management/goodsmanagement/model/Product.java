package com.management.goodsmanagement.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Product Name is required")
    private String productName;
    @NotBlank(message = "Product Description is required")
    private String productDescription;
    @NotBlank(message = "Product Brand is required")
    private String productBrand;
    @NotBlank(message = "Product price is required")
    private int productPrice;
    @JsonFormat(pattern = "yyyy-MMM-dd HH-mm-ss")
    private ZonedDateTime productCreated;

//    @JsonIgnore
    @ManyToOne(targetEntity = User.class, optional = false, cascade = CascadeType.PERSIST)
    private User productSeller;

    @JsonIgnore
    @ManyToMany(mappedBy = "productsWishList")
    private List<User> userList;

}
