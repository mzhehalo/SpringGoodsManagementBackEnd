package com.management.springgoodsmanagementbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String subCategory;
}
