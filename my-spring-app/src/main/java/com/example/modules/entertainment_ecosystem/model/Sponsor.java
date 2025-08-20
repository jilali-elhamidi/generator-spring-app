package com.example.modules.entertainment_ecosystem.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.entertainment_ecosystem.model.LiveEvent;import com.example.modules.entertainment_ecosystem.model.EventSponsorship;import com.example.modules.entertainment_ecosystem.model.AdCampaign;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;

@Entity
@Table(name = "sponsor_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Sponsor extends BaseEntity {

// === Attributs simples ===

        @NotNull@Size(min = 2, max = 100)
    private String name;

        @NotNull@Email
    private String contactEmail;

        
    private String companyType;


// === Relations ===

    @OneToMany(mappedBy = "sponsor", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("sponsor")
        private List<LiveEvent> sponsoredEvents;
    
    @OneToMany(mappedBy = "sponsor", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("sponsor")
        private List<EventSponsorship> sponsorships;
    
    @OneToMany(mappedBy = "advertiser", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("advertiser")
        private List<AdCampaign> adCampaigns;
    

}