package com.example.modules.entertainment_ecosystem.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.entertainment_ecosystem.model.Season;import com.example.modules.entertainment_ecosystem.model.Artist;import com.example.modules.entertainment_ecosystem.model.Genre;import com.example.modules.entertainment_ecosystem.model.Merchandise;import com.example.modules.entertainment_ecosystem.model.ProductionCompany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tvshow_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class TVShow extends BaseEntity {

// === Attributs simples ===

    @NotNull@Size(min = 2, max = 255)
    private String title;

    @NotNull@Min(1900)
    private Integer releaseYear;

    @NotNull@Min(1)
    private Integer totalSeasons;

    @Size(max = 1000)
    private String synopsis;


// === Relations ===

    @OneToMany(mappedBy = "show", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("show")
    private List<Season> seasons;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "director_id")
    @JsonIgnoreProperties("directedShows")
    private Artist director;
    
    @ManyToMany(fetch = FetchType.EAGER)
            @JoinTable(name = "tvshow_genres",
            joinColumns = @JoinColumn(name = "tvshow_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
            @JsonIgnoreProperties("")
            private List<Genre> genres;
            
    @ManyToMany(mappedBy = "", fetch = FetchType.LAZY)
            @JsonIgnoreProperties("")
            private List<Merchandise> relatedMerchandise;
        
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "company_id")
    @JsonIgnoreProperties("tvShows")
    private ProductionCompany productionCompany;
    

}