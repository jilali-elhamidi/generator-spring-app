package com.example.modules.entertainment_ecosystem.model;

// === Java / Jakarta ===
import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


// === Lombok ===
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tvshow_tbl")
public class TVShow extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 2, max = 255)
    @Column(unique = true, nullable = false)
    private String title;

    @NotNull
    @Min(1900)
    private Integer releaseYear;

    @NotNull
    @Min(1)
    private Integer totalSeasons;

    @Size(max = 1000)
    private String synopsis;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "director_id")
    @JsonIgnoreProperties("directedShows")
    private Artist director;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "company_id")
    @JsonIgnoreProperties("tvShows")
    private ProductionCompany productionCompany;
    
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
    

    // === Relations OneToMany ===
    @OneToMany(mappedBy = "show", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("show")
    private List<Season> seasons = new ArrayList<>();
    
    @OneToMany(mappedBy = "tvShow", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("tvShow")
    private List<StreamingContentLicense> streamingLicenses = new ArrayList<>();
    

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tvshow_genres",
        joinColumns = @JoinColumn(name = "tvshow_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id"))
    @JsonIgnoreProperties("tvShows")
    private List<Genre> genres = new ArrayList<>();
    
    
    @ManyToMany(mappedBy = "relatedShows", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("relatedShows")
    private List<Merchandise> relatedMerchandise = new ArrayList<>();
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tvshow_cast",
        joinColumns = @JoinColumn(name = "tvshow_id"),
        inverseJoinColumns = @JoinColumn(name = "artist_id"))
    @JsonIgnoreProperties("actedInShows")
    private List<Artist> cast = new ArrayList<>();
    
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tvshow_tags",
        joinColumns = @JoinColumn(name = "tag_id"),
        inverseJoinColumns = @JoinColumn(name = "tvshow_id"))
    @JsonIgnoreProperties("tvShows")
    private List<ContentTag> tags = new ArrayList<>();
    
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tvshow_languages",
        joinColumns = @JoinColumn(name = "tvshow_id"),
        inverseJoinColumns = @JoinColumn(name = "language_id"))
    @JsonIgnoreProperties("tvShows")
    private List<ContentLanguage> languages = new ArrayList<>();
    
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tvshow_platforms",
        joinColumns = @JoinColumn(name = "tvshow_id"),
        inverseJoinColumns = @JoinColumn(name = "platform_id"))
    @JsonIgnoreProperties("tvShows")
    private List<StreamingPlatform> platforms = new ArrayList<>();
    
    
}
