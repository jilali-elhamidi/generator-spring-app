package com.example.modules.entertainment_ecosystem.model;

// === Java / Jakarta ===
import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


// === Lombok ===
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "paymentmethod_tbl")
public class PaymentMethod extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 2, max = 50)
    @Column(unique = true, nullable = false)
    private String name;

    @NotNull
    @Size(min = 2, max = 50)
    private String type;


    // === Relations ManyToOne ===

    // === Relations OneToMany ===
    @OneToMany(mappedBy = "method", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("method")
    private List<Payment> payments = new ArrayList<>();
    

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
}
