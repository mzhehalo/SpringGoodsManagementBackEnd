package com.management.springgoodsmanagementbackend.dtos;

import com.management.springgoodsmanagementbackend.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserWithFirstNameDTO {

    private User user;

    private String firstName;
}
