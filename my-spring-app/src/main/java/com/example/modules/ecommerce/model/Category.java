package com.example.modules.ecommerce.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.ecommerce.model.Product;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "category_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Category extends BaseEntity {

// === Attributs simples ===

    @NotNull@Size(min = 2, max = 100)
    private String name;

    @Size(max = 255)
    private String description;


// === Relations ===

    @OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("category")
    private List<Product> products;
    

}