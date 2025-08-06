package com.example.modules.ecommerce.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.util.List;

import com.example.modules.ecommerce.model.User;

@Entity
@Table(name = "address_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Address extends BaseEntity {

// === Attributs simples ===

    
        
        @NotNull
        
            
            
            
                
                    @Size(min = 5, max = 100)
                
            
        
        
        
        
        
    
    private String street;

    
        
        @NotNull
        
            
            
            
                
                    @Size(min = 2, max = 50)
                
            
        
        
        
        
        
    
    private String city;

    
        
        @NotNull
        
            
            
            
                
                    @Size(min = 4, max = 10)
                
            
        
        
        
        
        
    
    private String postalCode;

    
        
        @NotNull
        
            
            
            
                
                    @Size(min = 2, max = 50)
                
            
        
        
        
        
        
    
    private String country;


// === Relations ===

    

    
        @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "user_id")
        @JsonIgnoreProperties("addresses")
        
        private User user;
    

    


// === Getters & Setters ===

    public String getStreet() {
    return street;
    }

    public void setStreet(String street) {
    this.street = street;
    }

    public String getCity() {
    return city;
    }

    public void setCity(String city) {
    this.city = city;
    }

    public String getPostalCode() {
    return postalCode;
    }

    public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
    }

    public String getCountry() {
    return country;
    }

    public void setCountry(String country) {
    this.country = country;
    }



    public User getUser() {
    return user;
    }

    public void setUser(User user) {
    this.user = user;
    }

}
