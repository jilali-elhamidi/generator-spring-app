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
@Table(name = "moviesoundtrack_tbl")
public class MovieSoundtrack extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 2, max = 255)
    @Column(unique = true, nullable = false)
    private String title;

    @NotNull
    private Date releaseDate;


    // === Relations ManyToOne ===

    // === Relations OneToMany ===
    @OneToMany(mappedBy = "soundtrack", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("soundtrack")
    private List<MusicTrack> musicTracks = new ArrayList<>();
    

    // === Relations OneToOne ===
    @OneToOne
    @JoinColumn(name = "movie_id")
    @JsonIgnoreProperties("soundtrack")
    private Movie movie;
    

    // === Relations ManyToMany ===
}
