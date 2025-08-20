package com.example.modules.entertainment_ecosystem.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.entertainment_ecosystem.model.SubscriptionPlan;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;

@Entity
@Table(name = "subscriptionfeature_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class SubscriptionFeature extends BaseEntity {

// === Attributs simples ===

        @NotNull@Size(min = 2, max = 100)
    private String name;

        @Size(max = 500)
    private String description;


// === Relations ===

    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "subscription_plan_features",
            joinColumns = @JoinColumn(name = "feature_id"),
            inverseJoinColumns = @JoinColumn(name = "plan_id"))
            @JsonIgnoreProperties("features")
            private List<SubscriptionPlan> subscriptionPlans;
            

}