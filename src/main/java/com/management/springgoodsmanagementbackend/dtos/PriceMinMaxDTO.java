package com.management.springgoodsmanagementbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceMinMaxDTO {
    private Integer priceMin;
    private Integer priceMax;
}
