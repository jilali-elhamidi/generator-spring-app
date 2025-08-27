package com.example.modules.entertainment_ecosystem.model;

// === Java / Jakarta ===
import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

// === Lombok ===
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "podcastepisode_tbl")
public class PodcastEpisode extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 2, max = 255)
    @Column(unique = true, nullable = false)
    private String title;

    @NotNull
    private Date releaseDate;

    @Min(1)
    private Integer durationMinutes;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "podcast_id")
    @JsonIgnoreProperties("episodes")
    private Podcast podcast;
    

    // === Relations OneToMany ===

    // === Relations OneToOne ===
    @OneToOne
    @JoinColumn(name = "")
    @JsonIgnoreProperties("relatedPodcastEpisode")
    private Episode relatedEpisode;
    

    // === Relations ManyToMany ===
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "podcast_guest_appearances",
        joinColumns = @JoinColumn(name = "episode_id"),
        inverseJoinColumns = @JoinColumn(name = "guest_id"))
    @JsonIgnoreProperties("appearances")
    private List<PodcastGuest> guestAppearances = new ArrayList<>();
    
    
}
