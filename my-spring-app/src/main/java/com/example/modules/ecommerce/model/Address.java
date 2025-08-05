package com.example.modules.ecommerce.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


    import com.example.modules.ecommerce.model.User;






@Entity
@Table(name = "address_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Address extends BaseEntity {


    private String street;

    private String city;

    private String postalCode;

    private String country;



    

    
        @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "user_id")
        @JsonIgnoreProperties("address") // éviter boucle
        private User user;
    

    


// Getters et Setters


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
