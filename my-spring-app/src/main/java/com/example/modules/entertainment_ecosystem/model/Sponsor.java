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
@Table(name = "sponsor_tbl")
public class Sponsor extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 2, max = 100)
    @Column(unique = true, nullable = false)
    private String name;

    @NotNull
    @Email
    private String contactEmail;

    private String companyType;


    // === Relations ManyToOne ===

    // === Relations OneToMany ===
    @OneToMany(mappedBy = "sponsor", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("sponsor")
    private List<LiveEvent> sponsoredEvents = new ArrayList<>();
    
    @OneToMany(mappedBy = "sponsor", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("sponsor")
    private List<EventSponsorship> sponsorships = new ArrayList<>();
    
    @OneToMany(mappedBy = "advertiser", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("advertiser")
    private List<AdCampaign> adCampaigns = new ArrayList<>();
    

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
}
