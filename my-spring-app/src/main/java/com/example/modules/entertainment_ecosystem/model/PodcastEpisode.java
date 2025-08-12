package com.example.modules.entertainment_ecosystem.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.Date;
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

    @NotNull
    @Size(min = 2, max = 255)
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

    @OneToOne(mappedBy = "relatedPodcastEpisode", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("relatedPodcastEpisode")
    private Episode relatedEpisode;  // Champ inverse, nom cohérent avec Episode.relatedPodcastEpisode

}
