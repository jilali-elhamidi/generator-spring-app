package com.example.modules.ecommerce.model;

// === Java / Jakarta ===
import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

// === Lombok ===
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "shipment_tbl")
public class Shipment extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Column(unique = true, nullable = false)
    private Date shipmentDate;

    @NotNull
    @Size(max = 100)
    private String carrier;

    @NotNull
    @Size(min = 2, max = 100)
    private String trackingNumber;


    // === Relations ManyToOne ===

    // === Relations OneToMany ===

    // === Relations OneToOne ===
    @OneToOne(mappedBy = "shipment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("shipment")
    private Order order;

    // === Relations ManyToMany ===
}
