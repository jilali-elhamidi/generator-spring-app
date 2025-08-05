package com.example.modules.ecommerce.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


    import com.example.modules.ecommerce.model.Product;

import java.util.List;


@Entity
@Table(name = "category_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Category extends BaseEntity {


    private String name;

    private String description;



    
        @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("category") // éviter boucle infinie
        private List<Product> products;
    

    

    


// Getters et Setters


    public String getName() {
    return name;
    }

    public void setName(String name) {
    this.name = name;
    }

    public String getDescription() {
    return description;
    }

    public void setDescription(String description) {
    this.description = description;
    }



    public List<Product> getProducts() {
    return products;
    }

    public void setProducts(List<Product> products) {
    this.products = products;
    }

}
