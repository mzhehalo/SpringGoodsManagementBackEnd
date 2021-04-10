package com.management.springgoodsmanagementbackend.dtos;

import com.management.springgoodsmanagementbackend.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProductWithIdDTO {
    private MultipartFile file;
    private Product product;
    private int id;
}
