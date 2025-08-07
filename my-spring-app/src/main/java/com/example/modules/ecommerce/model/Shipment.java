package com.example.modules.ecommerce.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.Date;
import com.example.modules.ecommerce.model.Order;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "shipment_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Shipment extends BaseEntity {

// === Attributs simples ===

    @NotNull
    private Date shipmentDate;

    @NotNull@Size(max = 100)
    private String carrier;

    @NotNull@Size(min = 2, max = 100)
    private String trackingNumber;


// === Relations ===

    @OneToOne
    @JoinColumn(name = "order_id")
    @JsonIgnoreProperties("order")
    private Order order;
            

}