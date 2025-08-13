package com.example.modules.entertainment_ecosystem.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.entertainment_ecosystem.model.Merchandise;import com.example.modules.entertainment_ecosystem.model.MerchandiseOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "merchandiseorderitem_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class MerchandiseOrderItem extends BaseEntity {

// === Attributs simples ===

    @NotNull@Min(1)
    private Integer quantity;

    @NotNull
    private Double priceAtPurchase;


// === Relations ===

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "merchandise_id")
    @JsonIgnoreProperties("orderItems")
    private Merchandise merchandiseItem;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "order_id")
    @JsonIgnoreProperties("items")
    private MerchandiseOrder order;
    

}