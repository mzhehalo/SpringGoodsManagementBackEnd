package com.management.springgoodsmanagementbackend.dtos;

import com.management.springgoodsmanagementbackend.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProductWithIdDTO {

    private Product product;

    private int id;
}
