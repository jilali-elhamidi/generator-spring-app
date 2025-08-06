package com.example.modules.ecommerce.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.Date;
import com.example.modules.ecommerce.model.Order;

@Entity
@Table(name = "payment_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Payment extends BaseEntity {

// === Attributs simples ===

    
        
        @NotNull
        
            
                
                    @Size(max = 50)
                
            
        
        
        
        
        
    
    private String method;

    
        
        @NotNull
        
            
                
            
        
        
        
        
        
    
    private Date paymentDate;

    
        
        @NotNull
        
            
                
            
        
        @Min(2)
        
        
        
    
    private Double amount;


// === Relations ===

    

    

    
        @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
        @JoinColumn(name = "")
        @JsonIgnoreProperties(value = "payment", allowSetters = true)
        private Order order;
    


// === Getters & Setters ===

    public String getMethod() {
    return method;
    }

    public void setMethod(String method) {
    this.method = method;
    }

    public Date getPaymentDate() {
    return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
    this.paymentDate = paymentDate;
    }

    public Double getAmount() {
    return amount;
    }

    public void setAmount(Double amount) {
    this.amount = amount;
    }



    public Order getOrder() {
    return order;
    }

    public void setOrder(Order order) {
    this.order = order;
    }

}
