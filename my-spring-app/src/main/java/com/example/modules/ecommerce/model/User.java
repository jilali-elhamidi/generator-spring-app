package com.example.modules.ecommerce.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.util.List;

import com.example.modules.ecommerce.model.Address;import com.example.modules.ecommerce.model.Order;

@Entity
@Table(name = "user_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User extends BaseEntity {

// === Attributs simples ===

    
        
        @NotNull
        
            
            
            
                
                    @Size(min = 3, max = 50)
                
            
        
        
        
        
        
    
    private String username;

    
        
        @NotNull
        
            
                
            
        
        
        
        
        @Email
    
    private String email;

    
        
        
        
            
                
                    @Size(max = 20)
                
            
        
        
        
        
        
    
    private String phone;


// === Relations ===

    
        @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("user")
        private List<Address> addresses;
    

    

    

    
        @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("user")
        private List<Order> orders;
    

    

    


// === Getters & Setters ===

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
