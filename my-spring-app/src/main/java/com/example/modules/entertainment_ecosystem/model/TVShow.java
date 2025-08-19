package com.example.modules.entertainment_ecosystem.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.entertainment_ecosystem.model.Season;import com.example.modules.entertainment_ecosystem.model.Artist;import com.example.modules.entertainment_ecosystem.model.Genre;import com.example.modules.entertainment_ecosystem.model.Merchandise;import com.example.modules.entertainment_ecosystem.model.ProductionCompany;import com.example.modules.entertainment_ecosystem.model.Artist;import com.example.modules.entertainment_ecosystem.model.StreamingContentLicense;import com.example.modules.entertainment_ecosystem.model.ContentProvider;import com.example.modules.entertainment_ecosystem.model.TVShowStudio;import com.example.modules.entertainment_ecosystem.model.ContentRating;import com.example.modules.entertainment_ecosystem.model.ContentTag;import com.example.modules.entertainment_ecosystem.model.ContentLanguage;import com.example.modules.entertainment_ecosystem.model.StreamingPlatform;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;

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
            @JsonIgnoreProperties("tvShows")
            private List<Genre> genres;
            
    @ManyToMany(mappedBy = "relatedShows", fetch = FetchType.LAZY)
            @JsonIgnoreProperties("relatedShows")
            private List<Merchandise> relatedMerchandise = new ArrayList<>();
        
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "company_id")
        @JsonIgnoreProperties("tvShows")
        private ProductionCompany productionCompany;
    
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "tvshow_cast",
            joinColumns = @JoinColumn(name = "tvshow_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id"))
            @JsonIgnoreProperties("actedInShows")
            private List<Artist> cast;
            
    @OneToMany(mappedBy = "tvShow", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("tvShow")
        private List<StreamingContentLicense> streamingLicenses;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "provider_id")
        @JsonIgnoreProperties("providedTvShows")
        private ContentProvider provider;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "tv_show_studio_id")
        @JsonIgnoreProperties("tvShows")
        private TVShowStudio tvShowStudio;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "content_rating_id")
        @JsonIgnoreProperties("ratedTvShows")
        private ContentRating contentRating;
    
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "tvshow_tags",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "tvshow_id"))
            @JsonIgnoreProperties("tvShows")
            private List<ContentTag> tags;
            
    @ManyToMany(fetch = FetchType.EAGER)
            @JoinTable(name = "tvshow_languages",
            joinColumns = @JoinColumn(name = "tvshow_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id"))
            @JsonIgnoreProperties("tvShows")
            private List<ContentLanguage> languages;
            
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "tvshow_platforms",
            joinColumns = @JoinColumn(name = "tvshow_id"),
            inverseJoinColumns = @JoinColumn(name = "platform_id"))
            @JsonIgnoreProperties("tvShows")
            private List<StreamingPlatform> platforms;
            

}