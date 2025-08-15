package com.example.modules.entertainment_ecosystem.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.model.Artist;import com.example.modules.entertainment_ecosystem.model.Artist;import com.example.modules.entertainment_ecosystem.model.Review;import com.example.modules.entertainment_ecosystem.model.Genre;import com.example.modules.entertainment_ecosystem.model.UserProfile;import com.example.modules.entertainment_ecosystem.model.Merchandise;import com.example.modules.entertainment_ecosystem.model.ProductionCompany;import com.example.modules.entertainment_ecosystem.model.DigitalPurchase;import com.example.modules.entertainment_ecosystem.model.MovieFormat;import com.example.modules.entertainment_ecosystem.model.StreamingContentLicense;import com.example.modules.entertainment_ecosystem.model.ContentProvider;import com.example.modules.entertainment_ecosystem.model.MovieStudio;import com.example.modules.entertainment_ecosystem.model.ContentRating;import com.example.modules.entertainment_ecosystem.model.ContentTag;import com.example.modules.entertainment_ecosystem.model.ContentLanguage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "movie_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Movie extends BaseEntity {

// === Attributs simples ===

    @NotNull@Size(min = 2, max = 255)
    private String title;

    @NotNull
    private Date releaseDate;

    @NotNull@Min(1)
    private Integer durationMinutes;

    @Size(max = 2000)
    private String synopsis;

    
    private Double boxOfficeRevenue;


// === Relations ===

    
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "movie_cast",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id"))
            @JsonIgnoreProperties("")
            private List<Artist> cast;
            
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "director_id")
        
        private Artist director;
    
    
    
    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<Review> reviews;
    
    
    @ManyToMany(fetch = FetchType.EAGER)
            @JoinTable(name = "movie_genres",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
            @JsonIgnoreProperties("")
            private List<Genre> genres;
            
    
    @ManyToMany(mappedBy = "", fetch = FetchType.LAZY)
            @JsonIgnoreProperties("")
            private List<UserProfile> watchlistUsers;
        
    
    @ManyToMany(mappedBy = "", fetch = FetchType.LAZY)
            @JsonIgnoreProperties("")
            private List<Merchandise> relatedMerchandise;
        
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "productionCompany_id")
        
        private ProductionCompany productionCompany;
    
    
    
    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<DigitalPurchase> purchases;
    
    
    @ManyToMany(fetch = FetchType.EAGER)
            @JoinTable(name = "movie_formats",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "format_id"))
            @JsonIgnoreProperties("")
            private List<MovieFormat> formats;
            
    
    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<StreamingContentLicense> streamingLicenses;
    
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "provider_id")
        
        private ContentProvider provider;
    
    
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "movieStudio_id")
        
        private MovieStudio movieStudio;
    
    
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "contentRating_id")
        
        private ContentRating contentRating;
    
    
    
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "movie_tags",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id"))
            @JsonIgnoreProperties("")
            private List<ContentTag> tags;
            
    
    @ManyToMany(fetch = FetchType.EAGER)
            @JoinTable(name = "movie_languages",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id"))
            @JsonIgnoreProperties("")
            private List<ContentLanguage> languages;
            

}