package com.management.springgoodsmanagementbackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.ZonedDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "First Name is required")
    private String firstName;
    @NotBlank(message = "Last Name is required")
    private String lastName;
    @Email
    @NotEmpty(message = "Email is required")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
    //    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ROLE_CUSTOMER")
//    @Column(nullable = false, columnDefinition = "ROLE_CUSTOMER")
    private String role;
    @JsonFormat(pattern = "yyyy-MMM-dd HH-mm-ss")
    private ZonedDateTime created;
    private boolean enabled;
    @JsonIgnore
//    @Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @ManyToMany(targetEntity = Product.class, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//    @Column
//    @org.hibernate.annotations.ForeignKey(name = "none")
//    @JoinColumn(name = "wishlist_product_id")
    @JoinTable
    private List<Product> productsWishList;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<CartProduct> cartProductList;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Ordering> orderingList;

    public void addProduct(Product product){
        this.productsWishList.add(product);
    }

    public void removeProduct(Product product){
        this.productsWishList.remove(product);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        return firstName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
