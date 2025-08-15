package com.example.modules.entertainment_ecosystem.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.entertainment_ecosystem.model.Movie;import com.example.modules.entertainment_ecosystem.model.TVShow;import com.example.modules.entertainment_ecosystem.model.VideoGame;import com.example.modules.entertainment_ecosystem.model.ContentRatingBoard;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "contentrating_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ContentRating extends BaseEntity {

// === Attributs simples ===

    @NotNull@Size(min = 2, max = 20)
    private String name;

    @Size(max = 200)
    private String description;


// === Relations ===

    
    @OneToMany(mappedBy = "contentRating", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<Movie> ratedMovies;
    
    
    @OneToMany(mappedBy = "contentRating", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<TVShow> ratedTvShows;
    
    
    @OneToMany(mappedBy = "contentRating", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<VideoGame> ratedVideoGames;
    
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "board_id")
        
        private ContentRatingBoard board;
    
    

}