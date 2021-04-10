package com.management.springgoodsmanagementbackend.dtos;

import com.management.springgoodsmanagementbackend.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWithEmailDTO {
    private User user;
    private String email;
}
