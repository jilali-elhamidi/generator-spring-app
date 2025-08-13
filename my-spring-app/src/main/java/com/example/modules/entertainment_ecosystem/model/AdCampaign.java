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
import com.example.modules.entertainment_ecosystem.model.Sponsor;import com.example.modules.entertainment_ecosystem.model.StreamingPlatform;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "adcampaign_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class AdCampaign extends BaseEntity {

// === Attributs simples ===

    @NotNull@Size(min = 2, max = 100)
    private String name;

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;

    @NotNull
    private Double budget;


// === Relations ===

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "advertiser_id")
    @JsonIgnoreProperties("adCampaigns")
    private Sponsor advertiser;
    
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "ad_campaign_platforms",
            joinColumns = @JoinColumn(name = "platform_id"),
            inverseJoinColumns = @JoinColumn(name = "campaign_id"))
            @JsonIgnoreProperties("")
            private List<StreamingPlatform> displayedOnPlatforms;
            

}