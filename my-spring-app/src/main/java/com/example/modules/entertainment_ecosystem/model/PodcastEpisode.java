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
import com.example.modules.entertainment_ecosystem.model.Podcast;import com.example.modules.entertainment_ecosystem.model.Episode;import com.example.modules.entertainment_ecosystem.model.PodcastGuest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "podcastepisode_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PodcastEpisode extends BaseEntity {

// === Attributs simples ===

    @NotNull@Size(min = 2, max = 255)
    private String title;

    @NotNull
    private Date releaseDate;

    @Min(1)
    private Integer durationMinutes;


// === Relations ===

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "podcast_id")
    @JsonIgnoreProperties("episodes")
    private Podcast podcast;
    
    @OneToOne
    @JoinColumn(name = "")
    @JsonIgnoreProperties("relatedPodcastEpisode")
    private Episode relatedEpisode;
            
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "podcast_guest_appearances",
            joinColumns = @JoinColumn(name = "episode_id"),
            inverseJoinColumns = @JoinColumn(name = "guest_id"))
            @JsonIgnoreProperties("")
            private List<PodcastGuest> guestAppearances;
            

}