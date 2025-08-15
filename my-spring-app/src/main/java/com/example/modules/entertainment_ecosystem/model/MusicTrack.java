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
import com.example.modules.entertainment_ecosystem.model.Album;import com.example.modules.entertainment_ecosystem.model.Artist;import com.example.modules.entertainment_ecosystem.model.Genre;import com.example.modules.entertainment_ecosystem.model.UserProfile;import com.example.modules.entertainment_ecosystem.model.PlaylistItem;import com.example.modules.entertainment_ecosystem.model.DigitalPurchase;import com.example.modules.entertainment_ecosystem.model.MusicFormat;import com.example.modules.entertainment_ecosystem.model.StreamingContentLicense;import com.example.modules.entertainment_ecosystem.model.ContentProvider;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "musictrack_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class MusicTrack extends BaseEntity {

// === Attributs simples ===

    @NotNull@Size(min = 2, max = 255)
    private String title;

    @NotNull@Min(1)
    private Integer durationSeconds;

    @NotNull
    private Date releaseDate;


// === Relations ===

    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "album_id")
        
        private Album album;
    
    
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "artist_id")
        
        private Artist artist;
    
    
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "genre_id")
        
        private Genre genre;
    
    
    
    @ManyToMany(mappedBy = "", fetch = FetchType.LAZY)
            @JsonIgnoreProperties("")
            private List<UserProfile> listenedByUsers;
        
    
    @OneToMany(mappedBy = "track", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<PlaylistItem> playlistItems;
    
    
    @OneToMany(mappedBy = "musicTrack", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<DigitalPurchase> purchases;
    
    
    @ManyToMany(fetch = FetchType.EAGER)
            @JoinTable(name = "music_formats",
            joinColumns = @JoinColumn(name = "track_id"),
            inverseJoinColumns = @JoinColumn(name = "format_id"))
            @JsonIgnoreProperties("")
            private List<MusicFormat> formats;
            
    
    @OneToMany(mappedBy = "musicTrack", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<StreamingContentLicense> streamingLicenses;
    
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "provider_id")
        
        private ContentProvider provider;
    
    

}