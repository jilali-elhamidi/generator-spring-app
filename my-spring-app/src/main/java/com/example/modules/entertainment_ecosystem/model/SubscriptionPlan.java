package com.example.modules.entertainment_ecosystem.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.entertainment_ecosystem.model.Subscription;import com.example.modules.entertainment_ecosystem.model.StreamingService;import com.example.modules.entertainment_ecosystem.model.StreamingContentLicense;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "subscriptionplan_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class SubscriptionPlan extends BaseEntity {

// === Attributs simples ===

    @NotNull@Size(min = 2, max = 100)
    private String name;

    @NotNull
    private Double price;

    @Size(max = 500)
    private String description;


// === Relations ===

    @OneToMany(mappedBy = "plan", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("plan")
    private List<Subscription> subscriptions;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "service_id")
    @JsonIgnoreProperties("plans")
    private StreamingService service;
    
    @OneToMany(mappedBy = "subscriptionPlan", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("subscriptionPlan")
    private List<StreamingContentLicense> includedStreamingContentLicenses;
    

}