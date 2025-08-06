package com.example.modules.ecommerce.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.util.List;

import com.example.modules.ecommerce.model.Category;

@Entity
@Table(name = "product_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product extends BaseEntity {

// === Attributs simples ===

    
        
        @NotNull
        
            
            
            
                
                    @Size(min = 2, max = 100)
                
            
        
        
        
        
        
    
    private String name;

    
        
        @NotNull
        
            
                
            
        
        
        
        
        
    
    private Integer stock;

    
        
        @NotNull
        
            
                
            
        
        @Min(2)
        
        
        
    
    private Double price;

    
        
        
        
            
                
                    @Size(max = 500)
                
            
        
        
        
        
        
    
    private String description;


// === Relations ===

    

    
        @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "category_id")
        @JsonIgnoreProperties("products")
        
        private Category category;
    

    


// === Getters & Setters ===

    public String getName() {
    return name;
    }

    public void setName(String name) {
    this.name = name;
    }

    public Integer getStock() {
    return stock;
    }

    public void setStock(Integer stock) {
    this.stock = stock;
    }

    public Double getPrice() {
    return price;
    }

    public void setPrice(Double price) {
    this.price = price;
    }

    public String getDescription() {
    return description;
    }

    public void setDescription(String description) {
    this.description = description;
    }



    public Category getCategory() {
    return category;
    }

    public void setCategory(Category category) {
    this.category = category;
    }

}
