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
@Table(name = "musictrack_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class MusicTrack extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 2, max = 255)
    private String title;

    @NotNull
    @Min(1)
    private Integer durationSeconds;

    @NotNull
    private Date releaseDate;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "album_id")
    @JsonIgnoreProperties("tracks")
    private Album album;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "artist_id")
    @JsonIgnoreProperties("composedMusic")
    private Artist artist;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "provider_id")
    @JsonIgnoreProperties("providedMusicTracks")
    private ContentProvider provider;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "soundtrack_id")
    @JsonIgnoreProperties("musicTracks")
    private MovieSoundtrack soundtrack;
    

    // === Relations OneToMany ===
    @OneToMany(mappedBy = "track", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("track")
    private List<PlaylistItem> playlistItems = new ArrayList<>();
    
    @OneToMany(mappedBy = "musicTrack", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("musicTrack")
    private List<DigitalPurchase> purchases = new ArrayList<>();
    
    @OneToMany(mappedBy = "musicTrack", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("musicTrack")
    private List<StreamingContentLicense> streamingLicenses = new ArrayList<>();
    
    @OneToMany(mappedBy = "musicTrack", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("musicTrack")
    private List<MusicVideo> musicVideos = new ArrayList<>();
    

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "music_track_genres",
        joinColumns = @JoinColumn(name = "track_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id"))
    @JsonIgnoreProperties("musicTracks")
    private List<Genre> genres = new ArrayList<>();
    
    
    @ManyToMany(mappedBy = "listenedMusic", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("listenedMusic")
    private List<UserProfile> listenedByUsers = new ArrayList<>();
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "music_formats",
        joinColumns = @JoinColumn(name = "track_id"),
        inverseJoinColumns = @JoinColumn(name = "format_id"))
    @JsonIgnoreProperties("musicTracks")
    private List<MusicFormat> formats = new ArrayList<>();
    
    
}
