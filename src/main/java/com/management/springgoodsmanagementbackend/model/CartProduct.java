package com.management.springgoodsmanagementbackend.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(targetEntity = User.class)
    private User customer;

    @ManyToOne
    private Product product;

    private int quantity;

    private boolean ordered;

}
