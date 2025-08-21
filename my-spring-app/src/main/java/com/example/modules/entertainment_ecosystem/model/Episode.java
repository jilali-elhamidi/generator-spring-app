package com.example.modules.entertainment_ecosystem.model;

// === Java / Jakarta ===
import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;


// === Jackson ===
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

// === Lombok ===
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "episode_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Episode extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Min(1)
        @Column(unique = true, nullable = false)
    private Integer episodeNumber;

    @NotNull
    @Size(min = 2, max = 255)
    private String title;

    @Size(max = 500)
    private String description;

    @Min(1)
    private Integer durationMinutes;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "season_id")
    @JsonIgnoreProperties("episodes")
    private Season season;
    

    // === Relations OneToMany ===
    @OneToMany(mappedBy = "episode", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("episode")
    private List<EpisodeCredit> credits = new ArrayList<>();
    
    @OneToMany(mappedBy = "episode", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("episode")
    private List<EpisodeReview> reviews = new ArrayList<>();
    

    // === Relations OneToOne ===
    @OneToOne
    @JoinColumn(name = "")
    @JsonIgnoreProperties("relatedEpisode")
    private PodcastEpisode relatedPodcastEpisode;
    

    // === Relations ManyToMany ===
    @ManyToMany(mappedBy = "watchedEpisodes", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("watchedEpisodes")
    private List<UserProfile> watchedByUsers = new ArrayList<>();
    
}
