package com.example.modules.ecommerce.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;






    import java.util.Date;


@Entity
@Table(name = "shipment_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Shipment extends BaseEntity {


    private Date shipmentDate;

    private String carrier;

    private String trackingNumber;




// Getters et Setters


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



}
