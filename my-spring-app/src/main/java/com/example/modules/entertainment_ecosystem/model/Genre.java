package com.example.modules.entertainment_ecosystem.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.entertainment_ecosystem.model.Movie;import com.example.modules.entertainment_ecosystem.model.TVShow;import com.example.modules.entertainment_ecosystem.model.Book;import com.example.modules.entertainment_ecosystem.model.MusicTrack;import com.example.modules.entertainment_ecosystem.model.UserProfile;import com.example.modules.entertainment_ecosystem.model.VideoGame;import com.example.modules.entertainment_ecosystem.model.Podcast;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;

@Entity
@Table(name = "genre_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Genre extends BaseEntity {

// === Attributs simples ===

        @NotNull@Size(min = 2, max = 50)
    private String name;


// === Relations ===

    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
            @JsonIgnoreProperties("genres")
            private List<Movie> movies = new ArrayList<>();
        
    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
            @JsonIgnoreProperties("")
            private List<TVShow> tvShows = new ArrayList<>();
        
    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
            @JsonIgnoreProperties("")
            private List<Book> bookGenres = new ArrayList<>();
        
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "genre_music_tracks",
            joinColumns = @JoinColumn(name = "genre_id"),
            inverseJoinColumns = @JoinColumn(name = "track_id"))
            @JsonIgnoreProperties("")
            private List<MusicTrack> musicTracks;
            
    @ManyToMany(mappedBy = "favoriteGenres", fetch = FetchType.LAZY)
            @JsonIgnoreProperties("favoriteGenres")
            private List<UserProfile> favoriteUsers = new ArrayList<>();
        
    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
            @JsonIgnoreProperties("")
            private List<VideoGame> videoGames = new ArrayList<>();
        
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "podcast_genres",
            joinColumns = @JoinColumn(name = "genre_id"),
            inverseJoinColumns = @JoinColumn(name = "podcast_id"))
            @JsonIgnoreProperties("genres")
            private List<Podcast> podcasts;
            

}