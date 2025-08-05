package com.example.modules.ecommerce.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


    import com.example.modules.ecommerce.model.Address;

    import com.example.modules.ecommerce.model.Order;

import java.util.List;


@Entity
@Table(name = "user_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User extends BaseEntity {


    private String username;

    private String email;

    private String phone;



    
        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("user") // éviter boucle infinie
        private List<Address> addresses;
    

    

    

    
        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("user") // éviter boucle infinie
        private List<Order> orders;
    

    

    


// Getters et Setters


    public String getUsername() {
    return username;
    }

    public void setUsername(String username) {
    this.username = username;
    }

    public String getEmail() {
    return email;
    }

    public void setEmail(String email) {
    this.email = email;
    }

    public String getPhone() {
    return phone;
    }

    public void setPhone(String phone) {
    this.phone = phone;
    }



    public List<Address> getAddresses() {
    return addresses;
    }

    public void setAddresses(List<Address> addresses) {
    this.addresses = addresses;
    }

    public List<Order> getOrders() {
    return orders;
    }

    public void setOrders(List<Order> orders) {
    this.orders = orders;
    }

}
