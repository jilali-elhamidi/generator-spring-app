package com.example.modules.entertainment_ecosystem.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.entertainment_ecosystem.model.Season;import com.example.modules.entertainment_ecosystem.model.UserProfile;import com.example.modules.entertainment_ecosystem.model.PodcastEpisode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "episode_tbl")
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Episode extends BaseEntity {

    @NotNull @Min(1)
    private Integer episodeNumber;

    @NotNull @Size(min = 2, max = 255)
    private String title;

    @Size(max = 500)
    private String description;

    @Min(1)
    private Integer durationMinutes;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "season_id")
    @JsonIgnoreProperties("episodes")
    private Season season;

    @ManyToMany(mappedBy = "watchedEpisodes", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("watchedByUsers")
    private List<UserProfile> watchedByUsers;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "related_podcast_episode_id")
    @JsonIgnoreProperties("episode")
    private PodcastEpisode relatedPodcastEpisode;
}
