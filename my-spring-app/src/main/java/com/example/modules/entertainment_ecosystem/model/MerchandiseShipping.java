package com.example.modules.entertainment_ecosystem.model;

// === Java / Jakarta ===
import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.Date;

// === Jackson ===
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

// === Lombok ===
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "merchandiseshipping_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class MerchandiseShipping extends BaseEntity {

    // === Attributs simples ===
    @NotNull
        @Column(unique = true, nullable = false)
    private Date shippingDate;

    @NotNull
    @Size(min = 2, max = 50)
    private String carrier;

    @NotNull
    @Size(min = 5, max = 50)
    private String trackingNumber;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "merchandise_id")
    @JsonIgnoreProperties("shipments")
    private Merchandise merchandiseItem;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "shipping_method_id")
    @JsonIgnoreProperties("shipments")
    private MerchandiseShippingMethod shippingMethod;
    

    // === Relations OneToMany ===
    @OneToMany(mappedBy = "shipment", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("shipment")
    private List<MerchandiseShippingStatus> statusHistory = new ArrayList<>();
    

    // === Relations OneToOne ===
    @OneToOne(mappedBy = "shippingDetails", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("shippingDetails")
    private MerchandiseOrder order;

    // === Relations ManyToMany ===
}
