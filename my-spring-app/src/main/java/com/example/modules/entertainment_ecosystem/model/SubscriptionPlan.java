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
@Table(name = "subscriptionplan_tbl")
public class SubscriptionPlan extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 2, max = 100)
    @Column(unique = true, nullable = false)
    private String name;

    @NotNull
    private Double price;

    @Size(max = 500)
    private String description;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "service_id")
    @JsonIgnoreProperties("plans")
    private StreamingService service;
    

    // === Relations OneToMany ===
    @OneToMany(mappedBy = "plan", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("plan")
    private List<Subscription> subscriptions = new ArrayList<>();
    
    @OneToMany(mappedBy = "subscriptionPlan", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("subscriptionPlan")
    private List<StreamingContentLicense> includedStreamingContentLicenses = new ArrayList<>();
    

    // === Relations OneToOne ===
    @OneToOne
    @JoinColumn(name = "tier_id")
    @JsonIgnoreProperties("subscriptionPlan")
    private SubscriptionTier tier;
    

    // === Relations ManyToMany ===
    @ManyToMany(mappedBy = "subscriptionPlans", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("subscriptionPlans")
    private List<SubscriptionFeature> features = new ArrayList<>();
    
}
