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
import com.example.modules.entertainment_ecosystem.model.SubscriptionPlan;import com.example.modules.entertainment_ecosystem.model.Movie;import com.example.modules.entertainment_ecosystem.model.TVShow;import com.example.modules.entertainment_ecosystem.model.MusicTrack;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "streamingcontentlicense_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class StreamingContentLicense extends BaseEntity {

// === Attributs simples ===

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;

    @NotNull@Size(min = 2, max = 50)
    private String region;


// === Relations ===

    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "subscriptionPlan_id")
        
        private SubscriptionPlan subscriptionPlan;
    
    
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "movie_id")
        
        private Movie movie;
    
    
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "tvShow_id")
        
        private TVShow tvShow;
    
    
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "musicTrack_id")
        
        private MusicTrack musicTrack;
    
    

}