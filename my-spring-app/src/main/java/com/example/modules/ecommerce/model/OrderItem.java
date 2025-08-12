package com.example.modules.ecommerce.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.ecommerce.model.Product;import com.example.modules.ecommerce.model.Order;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "orderitem_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class OrderItem extends BaseEntity {

// === Attributs simples ===

    @NotNull@Min(1)
    private Integer quantity;

    @NotNull@Min(2)
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
    

}