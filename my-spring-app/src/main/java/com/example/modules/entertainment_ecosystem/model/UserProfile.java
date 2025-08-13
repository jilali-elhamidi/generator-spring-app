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
import com.example.modules.entertainment_ecosystem.model.Review;import com.example.modules.entertainment_ecosystem.model.Movie;import com.example.modules.entertainment_ecosystem.model.Artist;import com.example.modules.entertainment_ecosystem.model.UserProfile;import com.example.modules.entertainment_ecosystem.model.UserProfile;import com.example.modules.entertainment_ecosystem.model.Genre;import com.example.modules.entertainment_ecosystem.model.Subscription;import com.example.modules.entertainment_ecosystem.model.Episode;import com.example.modules.entertainment_ecosystem.model.VideoGame;import com.example.modules.entertainment_ecosystem.model.ForumThread;import com.example.modules.entertainment_ecosystem.model.ForumPost;import com.example.modules.entertainment_ecosystem.model.Achievement;import com.example.modules.entertainment_ecosystem.model.OnlineEvent;import com.example.modules.entertainment_ecosystem.model.OnlineEvent;import com.example.modules.entertainment_ecosystem.model.Merchandise;import com.example.modules.entertainment_ecosystem.model.Podcast;import com.example.modules.entertainment_ecosystem.model.MusicTrack;import com.example.modules.entertainment_ecosystem.model.Playlist;import com.example.modules.entertainment_ecosystem.model.UserWallet;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "userprofile_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class UserProfile extends BaseEntity {

// === Attributs simples ===

    @NotNull@Size(min = 3, max = 50)
    private String username;

    @NotNull@Email
    private String email;

    @NotNull@Size(min = 8, max = 255)
    private String password;

    @NotNull
    private Date registrationDate;

    @Size(max = 50)
    private String country;

    @Size(max = 255)
    private String profilePictureUrl;

    private Date lastActiveDate;

    
    private String userStatus;

    @Size(max = 500)
    private String bio;


// === Relations ===

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user")
    private List<Review> reviews;
    
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "user_movie_watchlist",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id"))
            @JsonIgnoreProperties("")
            private List<Movie> watchlistMovies;
            
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "user_favorite_artists",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id"))
            @JsonIgnoreProperties("")
            private List<Artist> favoriteArtists;
            
    @ManyToMany(mappedBy = "", fetch = FetchType.LAZY)
            @JsonIgnoreProperties("")
            private List<UserProfile> followedUsers;
        
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "user_followers",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "followed_id"))
            @JsonIgnoreProperties("")
            private List<UserProfile> followingUsers;
            
    @ManyToMany(fetch = FetchType.EAGER)
            @JoinTable(name = "user_favorite_genres",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
            @JsonIgnoreProperties("")
            private List<Genre> favoriteGenres;
            
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user")
    private List<Subscription> subscriptions;
    
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "user_watched_episodes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "episode_id"))
            @JsonIgnoreProperties("")
            private List<Episode> watchedEpisodes;
            
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "user_games",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id"))
            @JsonIgnoreProperties("")
            private List<VideoGame> playedGames;
            
    @OneToMany(mappedBy = "author", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("author")
    private List<ForumThread> forumThreads;
    
    @OneToMany(mappedBy = "author", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("author")
    private List<ForumPost> forumPosts;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user")
    private List<Achievement> achievements;
    
    @OneToMany(mappedBy = "host", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("host")
    private List<OnlineEvent> hostedOnlineEvents;
    
    @ManyToMany(mappedBy = "", fetch = FetchType.LAZY)
            @JsonIgnoreProperties("")
            private List<OnlineEvent> attendedOnlineEvents;
        
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "user_merchandise",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "merchandise_id"))
            @JsonIgnoreProperties("")
            private List<Merchandise> ownedMerchandise;
            
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "user_podcast_library",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "podcast_id"))
            @JsonIgnoreProperties("")
            private List<Podcast> libraryPodcasts;
            
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "user_music_history",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "track_id"))
            @JsonIgnoreProperties("")
            private List<MusicTrack> listenedMusic;
            
    @OneToMany(mappedBy = "owner", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("owner")
    private List<Playlist> playlists;
    
    @OneToOne
    @JoinColumn(name = "wallet_id")
    @JsonIgnoreProperties("user")
    private UserWallet wallet;
            

}