package com.management.springgoodsmanagementbackend.dtos;

import com.management.springgoodsmanagementbackend.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductPageDTO {
    private List<Product> productList;
    private int number;
    private long totalElements;
    private int size;
}
