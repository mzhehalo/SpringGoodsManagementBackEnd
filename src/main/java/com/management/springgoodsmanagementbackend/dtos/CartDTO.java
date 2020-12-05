package com.management.springgoodsmanagementbackend.dtos;

import com.management.springgoodsmanagementbackend.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private Product product;

    private int quantity;
}
