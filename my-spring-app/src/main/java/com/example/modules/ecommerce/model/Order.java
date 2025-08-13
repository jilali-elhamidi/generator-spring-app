package com.example.modules.ecommerce.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Date;
import com.example.modules.ecommerce.model.User;import com.example.modules.ecommerce.model.OrderItem;import com.example.modules.ecommerce.model.Payment;import com.example.modules.ecommerce.model.Shipment;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "order_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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
    
    @OneToOne
    @JoinColumn(name = "payment_id")
    @JsonIgnoreProperties("order")
    private Payment payment;
            
    @OneToOne
    @JoinColumn(name = "shipment_id")
    @JsonIgnoreProperties("order")
    private Shipment shipment;
            

}