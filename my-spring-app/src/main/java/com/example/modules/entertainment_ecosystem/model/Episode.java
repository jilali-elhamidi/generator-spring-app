package com.example.modules.entertainment_ecosystem.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.entertainment_ecosystem.model.Season;import com.example.modules.entertainment_ecosystem.model.UserProfile;import com.example.modules.entertainment_ecosystem.model.PodcastEpisode;import com.example.modules.entertainment_ecosystem.model.EpisodeCredit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;

@Entity
@Table(name = "episode_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Episode extends BaseEntity {

// === Attributs simples ===

        @NotNull@Min(1)
    private Integer episodeNumber;

        @NotNull@Size(min = 2, max = 255)
    private String title;

        @Size(max = 500)
    private String description;

        @Min(1)
    private Integer durationMinutes;


// === Relations ===

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "season_id")
        @JsonIgnoreProperties("episodes")
        private Season season;
    
    @ManyToMany(mappedBy = "", fetch = FetchType.LAZY)
            @JsonIgnoreProperties("")
            private List<UserProfile> watchedByUsers = new ArrayList<>();
        
    @OneToOne
            @JoinColumn(name = "")
            @JsonIgnoreProperties("relatedEpisode")
            private PodcastEpisode relatedPodcastEpisode;
            
    @OneToMany(mappedBy = "episode", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("episode")
        private List<EpisodeCredit> credits;
    

}