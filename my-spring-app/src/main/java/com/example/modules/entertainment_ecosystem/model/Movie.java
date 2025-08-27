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
@Table(name = "movie_tbl")
public class Movie extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 2, max = 255)
    @Column(unique = true, nullable = false)
    private String title;

    @NotNull
    private Date releaseDate;

    @NotNull
    @Min(1)
    private Integer durationMinutes;

    @Size(max = 2000)
    private String synopsis;

    private Double boxOfficeRevenue;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "director_id")
    @JsonIgnoreProperties("directedMovies")
    private Artist director;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "company_id")
    @JsonIgnoreProperties("movies")
    private ProductionCompany productionCompany;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "provider_id")
    @JsonIgnoreProperties("providedMovies")
    private ContentProvider provider;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "movie_studio_id")
    @JsonIgnoreProperties("movies")
    private MovieStudio movieStudio;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "content_rating_id")
    @JsonIgnoreProperties("ratedMovies")
    private ContentRating contentRating;
    

    // === Relations OneToMany ===
    @OneToMany(mappedBy = "movie", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("movie")
    private List<Review> reviews = new ArrayList<>();
    
    @OneToMany(mappedBy = "movie", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("movie")
    private List<DigitalPurchase> purchases = new ArrayList<>();
    
    @OneToMany(mappedBy = "movie", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("movie")
    private List<StreamingContentLicense> streamingLicenses = new ArrayList<>();
    

    // === Relations OneToOne ===
    @OneToOne(mappedBy = "movie", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("movie")
    private MovieSoundtrack soundtrack;

    // === Relations ManyToMany ===
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "movie_cast",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "artist_id"))
    @JsonIgnoreProperties("actedInMovies")
    private List<Artist> cast = new ArrayList<>();
    
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "movie_genres",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id"))
    @JsonIgnoreProperties("movies")
    private List<Genre> genres = new ArrayList<>();
    
    
    @ManyToMany(mappedBy = "watchlistMovies", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("watchlistMovies")
    private List<UserProfile> watchlistUsers = new ArrayList<>();
    
    @ManyToMany(mappedBy = "relatedMovies", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("relatedMovies")
    private List<Merchandise> relatedMerchandise = new ArrayList<>();
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "movie_formats",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "format_id"))
    @JsonIgnoreProperties("movies")
    private List<MovieFormat> formats = new ArrayList<>();
    
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "movie_tags",
        joinColumns = @JoinColumn(name = "tag_id"),
        inverseJoinColumns = @JoinColumn(name = "movie_id"))
    @JsonIgnoreProperties("movies")
    private List<ContentTag> tags = new ArrayList<>();
    
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "movie_languages",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "language_id"))
    @JsonIgnoreProperties("movies")
    private List<ContentLanguage> languages = new ArrayList<>();
    
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "movie_platforms",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "platform_id"))
    @JsonIgnoreProperties("movies")
    private List<StreamingPlatform> platforms = new ArrayList<>();
    
    
    @ManyToMany(mappedBy = "movies", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("movies")
    private List<MovieFestival> festivals = new ArrayList<>();
    
}
