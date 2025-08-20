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
import com.example.modules.entertainment_ecosystem.model.UserProfile;import com.example.modules.entertainment_ecosystem.model.MusicTrack;import com.example.modules.entertainment_ecosystem.model.Album;import com.example.modules.entertainment_ecosystem.model.Book;import com.example.modules.entertainment_ecosystem.model.LiveEvent;import com.example.modules.entertainment_ecosystem.model.Podcast;import com.example.modules.entertainment_ecosystem.model.Merchandise;import com.example.modules.entertainment_ecosystem.model.VideoGame;import com.example.modules.entertainment_ecosystem.model.Manager;import com.example.modules.entertainment_ecosystem.model.ArtistAward;import com.example.modules.entertainment_ecosystem.model.Movie;import com.example.modules.entertainment_ecosystem.model.Movie;import com.example.modules.entertainment_ecosystem.model.TVShow;import com.example.modules.entertainment_ecosystem.model.DigitalAsset;import com.example.modules.entertainment_ecosystem.model.TVShow;import com.example.modules.entertainment_ecosystem.model.ArtistSocialMedia;import com.example.modules.entertainment_ecosystem.model.EpisodeCredit;import com.example.modules.entertainment_ecosystem.model.Audiobook;import com.example.modules.entertainment_ecosystem.model.MusicVideo;import com.example.modules.entertainment_ecosystem.model.ArtistGroup;import com.example.modules.entertainment_ecosystem.model.Tour;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;

@Entity
@Table(name = "artist_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Artist extends BaseEntity {

// === Attributs simples ===

        @NotNull@Size(min = 2, max = 100)
    private String name;

        @Size(max = 1000)
    private String bio;

        @NotNull
    private Date birthDate;

        @Size(max = 50)
    private String nationality;


// === Relations ===

    @ManyToMany(mappedBy = "favoriteArtists", fetch = FetchType.LAZY)
            @JsonIgnoreProperties("favoriteArtists")
            private List<UserProfile> favoriteArtists = new ArrayList<>();
        
    @OneToMany(mappedBy = "artist", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("artist")
        private List<MusicTrack> composedMusic;
    
    @OneToMany(mappedBy = "artist", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("artist")
        private List<Album> albums;
    
    @OneToMany(mappedBy = "author", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("author")
        private List<Book> booksAuthored;
    
    @ManyToMany(mappedBy = "performers", fetch = FetchType.LAZY)
            @JsonIgnoreProperties("performers")
            private List<LiveEvent> participatedInEvents = new ArrayList<>();
        
    @OneToMany(mappedBy = "host", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.EAGER)
        @JsonIgnoreProperties("host")
        private List<Podcast> hostedPodcasts;
    
    @OneToMany(mappedBy = "artist", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("artist")
        private List<Merchandise> managedMerchandise;
    
    @OneToMany(mappedBy = "developer", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("developer")
        private List<VideoGame> managedGames;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "manager_id")
        @JsonIgnoreProperties("artists")
        private Manager manager;
    
    @OneToMany(mappedBy = "artist", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("artist")
        private List<ArtistAward> awards;
    
    @ManyToMany(mappedBy = "cast", fetch = FetchType.LAZY)
            @JsonIgnoreProperties("cast")
            private List<Movie> actedInMovies = new ArrayList<>();
        
    @OneToMany(mappedBy = "director", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("director")
        private List<Movie> directedMovies;
    
    @OneToMany(mappedBy = "director", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("director")
        private List<TVShow> directedShows;
    
    @OneToMany(mappedBy = "artist", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("artist")
        private List<DigitalAsset> managedAssets;
    
    @ManyToMany(mappedBy = "cast", fetch = FetchType.LAZY)
            @JsonIgnoreProperties("cast")
            private List<TVShow> actedInShows = new ArrayList<>();
        
    @OneToMany(mappedBy = "artist", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("artist")
        private List<ArtistSocialMedia> socialMediaLinks;
    
    @OneToMany(mappedBy = "artist", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("artist")
        private List<EpisodeCredit> episodeCredits;
    
    @OneToMany(mappedBy = "author", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("author")
        private List<Audiobook> authoredAudiobooks;
    
    @OneToMany(mappedBy = "director", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("director")
        private List<MusicVideo> directedMusicVideos;
    
    @ManyToMany(mappedBy = "members", fetch = FetchType.LAZY)
            @JsonIgnoreProperties("members")
            private List<ArtistGroup> groups = new ArrayList<>();
        
    @OneToMany(mappedBy = "artist", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("artist")
        private List<Tour> tours;
    

}