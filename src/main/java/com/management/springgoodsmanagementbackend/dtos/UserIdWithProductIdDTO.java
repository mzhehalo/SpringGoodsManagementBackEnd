package com.management.springgoodsmanagementbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserIdWithProductIdDTO {
    private int productId;

    private int userId;
}
