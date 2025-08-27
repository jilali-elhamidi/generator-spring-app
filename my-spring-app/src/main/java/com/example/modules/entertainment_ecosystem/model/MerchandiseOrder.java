package com.example.modules.entertainment_ecosystem.model;

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
@Table(name = "merchandiseorder_tbl")
public class MerchandiseOrder extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Column(unique = true, nullable = false)
    private Date orderDate;

    @NotNull
    private String status;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("merchandiseOrders")
    private UserProfile user;
    

    // === Relations OneToMany ===
    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("order")
    private List<MerchandiseOrderItem> items = new ArrayList<>();
    

    // === Relations OneToOne ===
    @OneToOne
    @JoinColumn(name = "shipping_id")
    @JsonIgnoreProperties("order")
    private MerchandiseShipping shippingDetails;
    

    // === Relations ManyToMany ===
}
