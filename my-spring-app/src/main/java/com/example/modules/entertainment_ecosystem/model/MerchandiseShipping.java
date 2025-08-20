package com.example.modules.entertainment_ecosystem.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.model.Merchandise;import com.example.modules.entertainment_ecosystem.model.MerchandiseOrder;import com.example.modules.entertainment_ecosystem.model.MerchandiseShippingMethod;import com.example.modules.entertainment_ecosystem.model.MerchandiseShippingStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;

@Entity
@Table(name = "merchandiseshipping_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class MerchandiseShipping extends BaseEntity {

// === Attributs simples ===

        @NotNull
    private Date shippingDate;

        @NotNull@Size(min = 2, max = 50)
    private String carrier;

        @NotNull@Size(min = 5, max = 50)
    private String trackingNumber;


// === Relations ===

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "merchandise_id")
        @JsonIgnoreProperties("shipments")
        private Merchandise merchandiseItem;
    
    @OneToOne(mappedBy = "shippingDetails", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
            @JsonIgnoreProperties("shippingDetails")
            private MerchandiseOrder order;
        
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "shipping_method_id")
        @JsonIgnoreProperties("shipments")
        private MerchandiseShippingMethod shippingMethod;
    
    @OneToMany(mappedBy = "shipment", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("shipment")
        private List<MerchandiseShippingStatus> statusHistory;
    

}