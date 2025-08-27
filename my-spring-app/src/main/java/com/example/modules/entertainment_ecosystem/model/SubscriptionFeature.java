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
@Table(name = "subscriptionfeature_tbl")
public class SubscriptionFeature extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 2, max = 100)
    @Column(unique = true, nullable = false)
    private String name;

    @Size(max = 500)
    private String description;


    // === Relations ManyToOne ===

    // === Relations OneToMany ===

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "subscription_plan_features",
        joinColumns = @JoinColumn(name = "feature_id"),
        inverseJoinColumns = @JoinColumn(name = "plan_id"))
    @JsonIgnoreProperties("features")
    private List<SubscriptionPlan> subscriptionPlans = new ArrayList<>();
    
    
}
