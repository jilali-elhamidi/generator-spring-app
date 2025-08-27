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
@Table(name = "streamingplatform_tbl")
public class StreamingPlatform extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 2, max = 100)
    @Column(unique = true, nullable = false)
    private String name;

    @NotNull
    @Size(max = 255)
    private String website;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "service_id")
    @JsonIgnoreProperties("platforms")
    private StreamingService streamingService;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "online_platform_id")
    @JsonIgnoreProperties("streams")
    private OnlinePlatform onlinePlatform;
    

    // === Relations OneToMany ===
    @OneToMany(mappedBy = "platform", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("platform")
    private List<Subscription> subscriptions = new ArrayList<>();
    

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
    @ManyToMany(mappedBy = "platforms", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("platforms")
    private List<Movie> movies = new ArrayList<>();
    
    @ManyToMany(mappedBy = "platforms", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("platforms")
    private List<TVShow> tvShows = new ArrayList<>();
    
    @ManyToMany(mappedBy = "displayedOnPlatforms", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("displayedOnPlatforms")
    private List<AdCampaign> adCampaigns = new ArrayList<>();
    
}
