package com.management.springgoodsmanagementbackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    private String customerName;
    private String customerAddress;
    private String customerCountry;
    private int customerNumber;
    private boolean paid;

    @ManyToOne(targetEntity = User.class)
    private User customer;

    @JsonFormat(pattern = "yyyy-MMM-dd HH-mm-ss")
    private ZonedDateTime created;

    @ManyToMany
    private List<CartProduct> cartProductListOrder;
}
