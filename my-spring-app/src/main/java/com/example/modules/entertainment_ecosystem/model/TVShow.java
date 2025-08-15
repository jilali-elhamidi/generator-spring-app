package com.example.modules.entertainment_ecosystem.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.entertainment_ecosystem.model.Season;import com.example.modules.entertainment_ecosystem.model.Artist;import com.example.modules.entertainment_ecosystem.model.Genre;import com.example.modules.entertainment_ecosystem.model.Merchandise;import com.example.modules.entertainment_ecosystem.model.ProductionCompany;import com.example.modules.entertainment_ecosystem.model.Artist;import com.example.modules.entertainment_ecosystem.model.StreamingContentLicense;import com.example.modules.entertainment_ecosystem.model.ContentProvider;import com.example.modules.entertainment_ecosystem.model.TVShowStudio;import com.example.modules.entertainment_ecosystem.model.ContentRating;import com.example.modules.entertainment_ecosystem.model.ContentTag;import com.example.modules.entertainment_ecosystem.model.ContentLanguage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

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

    
    @OneToMany(mappedBy = "show", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<Season> seasons;
    
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "director_id")
        
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
        
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "productionCompany_id")
        
        private ProductionCompany productionCompany;
    
    
    
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "tvshow_cast",
            joinColumns = @JoinColumn(name = "tvshow_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id"))
            @JsonIgnoreProperties("")
            private List<Artist> cast;
            
    
    @OneToMany(mappedBy = "tvShow", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<StreamingContentLicense> streamingLicenses;
    
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "provider_id")
        
        private ContentProvider provider;
    
    
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "tvShowStudio_id")
        
        private TVShowStudio tvShowStudio;
    
    
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "contentRating_id")
        
        private ContentRating contentRating;
    
    
    
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "tvshow_tags",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "tvshow_id"))
            @JsonIgnoreProperties("")
            private List<ContentTag> tags;
            
    
    @ManyToMany(fetch = FetchType.EAGER)
            @JoinTable(name = "tvshow_languages",
            joinColumns = @JoinColumn(name = "tvshow_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id"))
            @JsonIgnoreProperties("")
            private List<ContentLanguage> languages;
            

}