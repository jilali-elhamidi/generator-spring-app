package com.example.modules.ecommerce.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.Date;
import com.example.modules.ecommerce.model.Order;

@Entity
@Table(name = "shipment_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Shipment extends BaseEntity {

// === Attributs simples ===

    
        
        @NotNull
        
            
                
            
        
        
        
        
        
    
    private Date shipmentDate;

    
        
        @NotNull
        
            
                
                    @Size(max = 100)
                
            
        
        
        
        
        
    
    private String carrier;

    
        
        @NotNull
        
            
                
                    @Size(max = 100)
                
            
        
        
        
        
        
    
    private String trackingNumber;


// === Relations ===

    

    

    
        @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
        @JoinColumn(name = "")
        @JsonIgnoreProperties(value = "shipment", allowSetters = true)
        private Order order;
    


// === Getters & Setters ===

    public Date getShipmentDate() {
    return shipmentDate;
    }

    public void setShipmentDate(Date shipmentDate) {
    this.shipmentDate = shipmentDate;
    }

    public String getCarrier() {
    return carrier;
    }

    public void setCarrier(String carrier) {
    this.carrier = carrier;
    }

    public String getTrackingNumber() {
    return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
    this.trackingNumber = trackingNumber;
    }



    public Order getOrder() {
    return order;
    }

    public void setOrder(Order order) {
    this.order = order;
    }

}
