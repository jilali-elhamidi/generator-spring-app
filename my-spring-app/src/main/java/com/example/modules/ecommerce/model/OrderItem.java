package com.example.modules.ecommerce.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.util.List;

import com.example.modules.ecommerce.model.Product;import com.example.modules.ecommerce.model.Order;

@Entity
@Table(name = "orderitem_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrderItem extends BaseEntity {

// === Attributs simples ===

    
        
        @NotNull
        
            
                
            
        
        @Min(1)
        
        
        
    
    private Integer quantity;

    
        
        @NotNull
        
            
                
            
        
        @Min(2)
        
        
        
    
    private Double price;


// === Relations ===

    

    
        @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "product_id")
        @JsonIgnoreProperties("orderItems")
        
        private Product product;
    

    

    

    
        @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "order_id")
        @JsonIgnoreProperties("orderItems")
        
        private Order order;
    

    


// === Getters & Setters ===

    public Integer getQuantity() {
    return quantity;
    }

    public void setQuantity(Integer quantity) {
    this.quantity = quantity;
    }

    public Double getPrice() {
    return price;
    }

    public void setPrice(Double price) {
    this.price = price;
    }



    public Product getProduct() {
    return product;
    }

    public void setProduct(Product product) {
    this.product = product;
    }

    public Order getOrder() {
    return order;
    }

    public void setOrder(Order order) {
    this.order = order;
    }

}
