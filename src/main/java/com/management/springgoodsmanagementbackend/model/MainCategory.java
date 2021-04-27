package com.management.springgoodsmanagementbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MainCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String mainCategory;

    @OneToMany(cascade = CascadeType.ALL)
    private List<SubCategory> subCategories;
}
