package com.example.modules.entertainment_ecosystem.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.entertainment_ecosystem.model.LiveEvent;import com.example.modules.entertainment_ecosystem.model.Movie;import com.example.modules.entertainment_ecosystem.model.TVShow;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "sponsor_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Sponsor extends BaseEntity {

// === Attributs simples ===

    @NotNull@Size(min = 2, max = 100)
    private String name;

    @NotNull@Email
    private String contactEmail;


// === Relations ===

    @OneToMany(mappedBy = "sponsor", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("sponsor")
    private List<LiveEvent> sponsoredEvents;
    
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "movie_sponsors",
            joinColumns = @JoinColumn(name = "sponsor_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id"))
            @JsonIgnoreProperties("")
            private List<Movie> sponsoredMovies;
            
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "tvshow_sponsors",
            joinColumns = @JoinColumn(name = "sponsor_id"),
            inverseJoinColumns = @JoinColumn(name = "tvshow_id"))
            @JsonIgnoreProperties("")
            private List<TVShow> sponsoredShows;
            

}