package com.management.springgoodsmanagementbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductIdWithCustomerId {
    private int productId;

    private int customerId;
}
