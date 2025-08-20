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
@Table(name = "genre_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Genre extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 2, max = 50)
    private String name;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties("genres")
    private MusicGenreCategory category;
    

    // === Relations OneToMany ===

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("genres")
    private List<Movie> movies = new ArrayList<>();
    
    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("genres")
    private List<TVShow> tvShows = new ArrayList<>();
    
    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("genres")
    private List<Book> bookGenres = new ArrayList<>();
    
    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("genres")
    private List<MusicTrack> musicTracks = new ArrayList<>();
    
    @ManyToMany(mappedBy = "favoriteGenres", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("favoriteGenres")
    private List<UserProfile> favoriteUsers = new ArrayList<>();
    
    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("genres")
    private List<VideoGame> videoGames = new ArrayList<>();
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "podcast_genres",
        joinColumns = @JoinColumn(name = "genre_id"),
        inverseJoinColumns = @JoinColumn(name = "podcast_id"))
    @JsonIgnoreProperties("genres")
    private List<Podcast> podcasts = new ArrayList<>();
    
    
    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("genres")
    private List<Album> albums = new ArrayList<>();
    
}
