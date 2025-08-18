package com.example.modules.ecommerce.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.ecommerce.model.Address;import com.example.modules.ecommerce.model.Order;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "user_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User extends BaseEntity {

// === Attributs simples ===

        @NotNull@Size(min = 3, max = 50)
    private String username;

        @NotNull@Email
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
    

}