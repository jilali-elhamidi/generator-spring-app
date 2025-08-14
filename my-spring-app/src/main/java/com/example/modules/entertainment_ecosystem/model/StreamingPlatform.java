package com.example.modules.entertainment_ecosystem.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.entertainment_ecosystem.model.Movie;import com.example.modules.entertainment_ecosystem.model.TVShow;import com.example.modules.entertainment_ecosystem.model.Subscription;import com.example.modules.entertainment_ecosystem.model.StreamingService;import com.example.modules.entertainment_ecosystem.model.OnlinePlatform;import com.example.modules.entertainment_ecosystem.model.AdCampaign;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "streamingplatform_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class StreamingPlatform extends BaseEntity {

// === Attributs simples ===

    @NotNull@Size(min = 2, max = 100)
    private String name;

    @NotNull@Size(max = 255)
    private String website;


// === Relations ===

    
    @ManyToMany(mappedBy = "", fetch = FetchType.LAZY)
            @JsonIgnoreProperties("")
            private List<Movie> movies;
        
    
    @ManyToMany(mappedBy = "", fetch = FetchType.LAZY)
            @JsonIgnoreProperties("")
            private List<TVShow> tvShows;
        
    
    @OneToMany(mappedBy = "platform", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<Subscription> subscriptions;
    
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "streamingService_id")
        
        private StreamingService streamingService;
    
    
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "onlinePlatform_id")
        
        private OnlinePlatform onlinePlatform;
    
    
    
    @ManyToMany(mappedBy = "", fetch = FetchType.LAZY)
            @JsonIgnoreProperties("")
            private List<AdCampaign> adCampaigns;
        

}