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
@Table(name = "contentrating_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ContentRating extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 2, max = 20)
    @Column(unique = true, nullable = false)
    private String name;

    @Size(max = 200)
    private String description;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "board_id")
    @JsonIgnoreProperties("ratings")
    private ContentRatingBoard board;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "age_group_id")
    @JsonIgnoreProperties("contentRatings")
    private ContentRatingAgeGroup ageGroup;
    

    // === Relations OneToMany ===
    @OneToMany(mappedBy = "contentRating", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("contentRating")
    private List<Movie> ratedMovies = new ArrayList<>();
    
    @OneToMany(mappedBy = "contentRating", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("contentRating")
    private List<TVShow> ratedTvShows = new ArrayList<>();
    
    @OneToMany(mappedBy = "contentRating", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("contentRating")
    private List<VideoGame> ratedVideoGames = new ArrayList<>();
    

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
}
