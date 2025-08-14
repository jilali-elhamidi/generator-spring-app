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
import com.example.modules.entertainment_ecosystem.model.UserProfile;import com.example.modules.entertainment_ecosystem.model.StreamingPlatform;import com.example.modules.entertainment_ecosystem.model.SubscriptionPlan;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "subscription_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Subscription extends BaseEntity {

// === Attributs simples ===

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;

    @NotNull
    private String status;


// === Relations ===

    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "user_id")
        
        private UserProfile user;
    
    
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "platform_id")
        
        private StreamingPlatform platform;
    
    
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "plan_id")
        
        private SubscriptionPlan plan;
    
    

}