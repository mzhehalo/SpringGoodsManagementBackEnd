package com.management.springgoodsmanagementbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserWithFirstName {

    private User user;

    private String firstName;
}