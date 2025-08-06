package com.example.modules.ecommerce.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.Date;
import com.example.modules.ecommerce.model.User;import com.example.modules.ecommerce.model.OrderItem;import com.example.modules.ecommerce.model.Payment;import com.example.modules.ecommerce.model.Shipment;

@Entity
@Table(name = "order_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Order extends BaseEntity {

// === Attributs simples ===

    
        
        @NotNull
        
            
                
            
        
        
        
        
        
    
    private Date orderDate;

    
        
        @NotNull
        
            
                
            
        
        
        
        
        
    
    private String status;


// === Relations ===

    

    
        @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "user_id")
        @JsonIgnoreProperties("orders")
        
        private User user;
    

    

    
        @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.EAGER)
        @JsonIgnoreProperties("order")
        private List<OrderItem> orderItems;
    

    

    

    

    

    
        @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
        @JoinColumn(name = "payment_id")
        @JsonIgnoreProperties(value = "order", allowSetters = true)
        private Payment payment;
    

    

    

    
        @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
        @JoinColumn(name = "shipment_id")
        @JsonIgnoreProperties(value = "order", allowSetters = true)
        private Shipment shipment;
    


// === Getters & Setters ===

    public Date getOrderDate() {
    return orderDate;
    }

    public void setOrderDate(Date orderDate) {
    this.orderDate = orderDate;
    }

    public String getStatus() {
    return status;
    }

    public void setStatus(String status) {
    this.status = status;
    }



    public User getUser() {
    return user;
    }

    public void setUser(User user) {
    this.user = user;
    }

    public List<OrderItem> getOrderItems() {
    return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
    this.orderItems = orderItems;
    }

    public Payment getPayment() {
    return payment;
    }

    public void setPayment(Payment payment) {
    this.payment = payment;
    }

    public Shipment getShipment() {
    return shipment;
    }

    public void setShipment(Shipment shipment) {
    this.shipment = shipment;
    }

}
