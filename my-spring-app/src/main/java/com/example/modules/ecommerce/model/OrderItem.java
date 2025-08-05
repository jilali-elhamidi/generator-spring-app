package com.example.modules.ecommerce.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


    import com.example.modules.ecommerce.model.Product;

    import com.example.modules.ecommerce.model.Order;






@Entity
@Table(name = "orderitem_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrderItem extends BaseEntity {


    private Integer quantity;

    private Double price;



    

    
        @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "product_id")
        @JsonIgnoreProperties("orderitem") // éviter boucle
        private Product product;
    

    

    

    
        @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "order_id")
        @JsonIgnoreProperties("orderitem") // éviter boucle
        private Order order;
    

    


// Getters et Setters


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
