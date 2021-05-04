package com.management.springgoodsmanagementbackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

public class Ordering {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank(message = "Customer name is required!")
    private String customerName;
    @NotBlank(message = "Customer Address is required!")
    private String customerAddress;
    @NotBlank(message = "Customer Country is required!")
    private String customerCountry;
    @NotBlank(message = "Customer Number is required!")
    private String customerNumber;
    private boolean paid;

    @ManyToOne(targetEntity = User.class)
    private User customer;

    @JsonFormat(pattern = "yyyy-MMM-dd HH-mm-ss")
    private ZonedDateTime created;

    @ManyToMany
    private List<CartProduct> cartProductListOrder;
}
