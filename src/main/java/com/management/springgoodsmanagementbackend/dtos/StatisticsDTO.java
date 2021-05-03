package com.management.springgoodsmanagementbackend.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class StatisticsDTO {
    private long quantityOrders;
    private long quantityOrdersAll;
    private int sumPriceOrdered;
    private int sumPriceOrderedAll;
    private long numberOfProducts;
    private long numberOfProductsAll;
    private int sumPrice;
    private int sumPriceAll;
}
