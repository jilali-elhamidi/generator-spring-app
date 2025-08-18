package com.example.modules.ecommerce.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.ecommerce.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "address_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Address extends BaseEntity {

// === Attributs simples ===

        @NotNull@Size(min = 5, max = 100)
    private String street;

        @NotNull@Size(min = 2, max = 50)
    private String city;

        @NotNull@Size(min = 4, max = 10)
    private String postalCode;

        @NotNull@Size(min = 2, max = 50)
    private String country;


// === Relations ===

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "user_id")
        @JsonIgnoreProperties("addresses")
        private User user;
    

}