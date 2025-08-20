package com.example.modules.entertainment_ecosystem.model;

// === Java / Jakarta ===
import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.Date;

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
@Table(name = "artist_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Artist extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 2, max = 100)
    private String name;

    @Size(max = 1000)
    private String bio;

    @NotNull
    private Date birthDate;

    @Size(max = 50)
    private String nationality;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "manager_id")
    @JsonIgnoreProperties("artists")
    private Manager manager;
    

    // === Relations OneToMany ===
    @OneToMany(mappedBy = "artist", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("artist")
    private List<MusicTrack> composedMusic = new ArrayList<>();
    
    @OneToMany(mappedBy = "artist", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("artist")
    private List<Album> albums = new ArrayList<>();
    
    @OneToMany(mappedBy = "author", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("author")
    private List<Book> booksAuthored = new ArrayList<>();
    
    @OneToMany(mappedBy = "host", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("host")
    private List<Podcast> hostedPodcasts = new ArrayList<>();
    
    @OneToMany(mappedBy = "artist", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("artist")
    private List<Merchandise> managedMerchandise = new ArrayList<>();
    
    @OneToMany(mappedBy = "developer", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("developer")
    private List<VideoGame> managedGames = new ArrayList<>();
    
    @OneToMany(mappedBy = "artist", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("artist")
    private List<ArtistAward> awards = new ArrayList<>();
    
    @OneToMany(mappedBy = "director", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("director")
    private List<Movie> directedMovies = new ArrayList<>();
    
    @OneToMany(mappedBy = "director", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("director")
    private List<TVShow> directedShows = new ArrayList<>();
    
    @OneToMany(mappedBy = "artist", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("artist")
    private List<DigitalAsset> managedAssets = new ArrayList<>();
    
    @OneToMany(mappedBy = "artist", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("artist")
    private List<ArtistSocialMedia> socialMediaLinks = new ArrayList<>();
    
    @OneToMany(mappedBy = "artist", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("artist")
    private List<EpisodeCredit> episodeCredits = new ArrayList<>();
    
    @OneToMany(mappedBy = "author", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("author")
    private List<Audiobook> authoredAudiobooks = new ArrayList<>();
    
    @OneToMany(mappedBy = "director", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("director")
    private List<MusicVideo> directedMusicVideos = new ArrayList<>();
    
    @OneToMany(mappedBy = "artist", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("artist")
    private List<Tour> tours = new ArrayList<>();
    

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
    @ManyToMany(mappedBy = "favoriteArtists", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("favoriteArtists")
    private List<UserProfile> favoriteArtists = new ArrayList<>();
    
    @ManyToMany(mappedBy = "performers", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("performers")
    private List<LiveEvent> participatedInEvents = new ArrayList<>();
    
    @ManyToMany(mappedBy = "cast", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("cast")
    private List<Movie> actedInMovies = new ArrayList<>();
    
    @ManyToMany(mappedBy = "cast", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("cast")
    private List<TVShow> actedInShows = new ArrayList<>();
    
    @ManyToMany(mappedBy = "members", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("members")
    private List<ArtistGroup> groups = new ArrayList<>();
    
}
